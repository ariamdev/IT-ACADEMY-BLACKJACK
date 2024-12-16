package itacademy.S05.BLACKJACK.service.impl;

import itacademy.S05.BLACKJACK.exception.custom.DeckEmptyException;
import itacademy.S05.BLACKJACK.exception.custom.GameNotFoundException;
import itacademy.S05.BLACKJACK.exception.custom.PlayerNotFoundException;
import itacademy.S05.BLACKJACK.model.Game;
import itacademy.S05.BLACKJACK.model.cards.AceCard;
import itacademy.S05.BLACKJACK.model.cards.Card;
import itacademy.S05.BLACKJACK.model.cards.NonAceCard;
import itacademy.S05.BLACKJACK.model.enums.GameStatus;
import itacademy.S05.BLACKJACK.model.enums.PlayerStatus;
import itacademy.S05.BLACKJACK.model.enums.Suit;
import itacademy.S05.BLACKJACK.model.participants.Croupier;
import itacademy.S05.BLACKJACK.model.participants.Player;
import itacademy.S05.BLACKJACK.model.participants.PlayerPlaying;
import itacademy.S05.BLACKJACK.repository.GameRepository;
import itacademy.S05.BLACKJACK.service.GameService;
import itacademy.S05.BLACKJACK.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class GameServiceImpl implements GameService {


    private GameRepository gameRepository;
    private List<Card> deck;
    private PlayerService playerService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, PlayerService playerService) {
        this.gameRepository = gameRepository;
        this.deck = deck;
        this.playerService = playerService;
    }

    @Override
    public Mono<Game> createGame(String name) {
        return initGame(name)
                .flatMap(game -> {
                    if (game.getPlayerHand() == null || game.getCroupierHand() == null) {
                        throw new DeckEmptyException("Deck does not have enough cards.");
                    }
                    return gameRepository.save(game);
                })
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player could not be created.")));
    }

    @Override
    public Mono<Game> getGameInfo(String id) {
        return gameRepository.findById(id)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with the id " + id + ".")));
    }

    @Override
    public Mono<Game> makeMove(String id, String moveType) {
        return gameRepository.findById(id)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with the id " + id)))
                .flatMap(game -> {
                    if (moveType.equalsIgnoreCase("HIT")) {
                        return handleHit(game)
                                .flatMap(updatedGame -> checkWinner(updatedGame));
                    } else if (moveType.equalsIgnoreCase("STAND")) {
                        return croupierTurn(game)
                                .flatMap(updatedGame -> checkWinner(updatedGame));
                    } else {
                        return Mono.error(new IllegalArgumentException("Invalid move type. Only 'HIT' or 'STAND' are allowed. Don't cheat!"));
                    }
                });
    }

    @Override
    public Mono<Void> deleteGame(String id) {
        return gameRepository.findById(id)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with the id " + id)))
                .flatMap(game -> gameRepository.delete(game));
    }

    private List<Card> generateCards() {
        List<Card> deck = new ArrayList<>();

        for(Suit suit : Suit.values()){
            for(int i= 2; i<=10; i++ ){
                deck.add(new NonAceCard(suit,i,String.valueOf(i)));
            }

            deck.add(new NonAceCard(suit,10,"J"));
            deck.add(new NonAceCard(suit,10,"Q"));
            deck.add(new NonAceCard(suit,10,"K"));
            deck.add(new AceCard(suit, 11,"As"));
        }

        return deck;
    }

    public Mono<Player> createPlayer(String name) {
        return playerService.createPlayer(name);
    }

    public List<Card> dealInitialPlayerCards(List<Card> deck) {
        List<Card> playerHand = new ArrayList<>();
        playerHand.add(deck.remove(0));
        playerHand.add(deck.remove(0));
        return playerHand;
    }

    public List<Card> dealInitCroupierHand(List<Card> deck) {
        List<Card> croupierHand = new ArrayList<>();
        croupierHand.add(deck.remove(0));
        croupierHand.add(deck.remove(0));
        return croupierHand;
    }


    public Mono<Game> initGame(String playerName) {
        List<Card> deck = generateCards();
        Collections.shuffle(deck); //Barajar las cartas random
        return createPlayer(playerName)
                .map(player -> {
                    PlayerPlaying playerPlaying = new PlayerPlaying(player.getId(),player.getName(),player.getTotalPoints(),player.getStatus());
                    Croupier croupier = new Croupier();
                    List<Card> playerHand = dealInitialPlayerCards(deck);
                    List<Card> croupierHand = dealInitCroupierHand(deck);

                    return Game.builder()
                            .playerPlaying(playerPlaying)
                            .croupier(croupier)
                            .status(GameStatus.ACTIVE)
                            .playerHand(playerHand)
                            .croupierHand(croupierHand)
                            .deck(deck)
                            .build();
                });
    }

    public int calculateHandValue(List<Card> hand) {
        int totalValue = 0;
        int aceCount = 0;

        for (Card card : hand) {
            totalValue += card.getValue();
            if (card instanceof AceCard) {
                aceCount++;
            }
        }
        while (totalValue > 21 && aceCount > 0) {
            totalValue -= 10;
            aceCount--;
        }

        return totalValue;
    }

    public Mono<Game> handleHit(Game game) {
        List<Card> deck = game.getDeck();

        if (deck == null || deck.isEmpty()) {
            return Mono.error(new DeckEmptyException("Deck is empty"));
        }

        Card newCard = deck.remove(0);
        game.getPlayerHand().add(newCard);


        int playerValue = calculateHandValue(game.getPlayerHand());
        if (playerValue > 21) {
            game.getPlayerPlaying().setStatus(PlayerStatus.LOST);
            game.getPlayerPlaying().setTotalPoints(+2);
            game.setStatus(GameStatus.FINISHED);

        }
        return gameRepository.save(game);
    }

    public Mono<Game> croupierTurn(Game game) {

        List<Card> deck = game.getDeck();
        List<Card> croupierHand = game.getCroupierHand();
        int handValue = calculateHandValue(croupierHand);

        while (handValue < 17 && !deck.isEmpty()) {
            Card newCard = deck.remove(0);
            croupierHand.add(newCard);
            handValue = calculateHandValue(croupierHand);
        }
        return Mono.just(game);
    }

    public Mono<Game> checkWinner(Game game) {
        int playerValue = calculateHandValue(game.getPlayerHand());
        int croupierValue = calculateHandValue(game.getCroupierHand());
        Mono <Player> updatedPlayer = null;

        if (playerValue > 21 || (croupierValue <= 21 && croupierValue > playerValue)) {
            game.getPlayerPlaying().setStatus(PlayerStatus.LOST);
            game.getPlayerPlaying().setTotalPoints(+2);
            game.getCroupier().setStatus(PlayerStatus.WON);

            int id = game.getPlayerPlaying().getId();
            updatedPlayer = playerService.updatePoints(id, game.getPlayerPlaying().getTotalPoints());

        } else if (croupierValue > 21 || playerValue > croupierValue) {
            game.getPlayerPlaying().setStatus(PlayerStatus.WON);
            game.getPlayerPlaying().setTotalPoints(+10);
            game.getCroupier().setStatus(PlayerStatus.LOST);

            int id = game.getPlayerPlaying().getId();
            updatedPlayer = playerService.updatePoints(id, game.getPlayerPlaying().getTotalPoints());

        } else {
            game.getPlayerPlaying().setStatus(PlayerStatus.DRAW);
            game.getCroupier().setStatus(PlayerStatus.DRAW);
        }

        game.setStatus(GameStatus.FINISHED);
        return updatedPlayer
                .then(gameRepository.save(game));
    }

}
