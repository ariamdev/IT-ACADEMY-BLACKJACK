package itacademy.S05.BLACKJACK.service;

import itacademy.S05.BLACKJACK.model.Game;
import itacademy.S05.BLACKJACK.model.participants.Player;
import reactor.core.publisher.Mono;

import java.util.List;

public interface GameService {
    Mono<Game> createGame(String name);
    Mono<Game> getGameInfo(String id);
    Mono<Game> makeMove(String id, String moveType);
    Mono<Void> deleteGame(String id);
}
