package itacademy.S05.BLACKJACK.model.DTO;

import itacademy.S05.BLACKJACK.model.Game;
import itacademy.S05.BLACKJACK.model.enums.GameStatus;
import itacademy.S05.BLACKJACK.model.participants.PlayerPlaying;

public class GameMapperDTO {

    public static GameDTO toGameDTO(Game game) {
        return GameDTO.builder()
                .id(game.getId())
                .playerPlaying(toPlayerPlayingDTO(game.getPlayerPlaying()))
                .croupier(new CroupierDTO(game.getCroupier().getStatus()))
                .status(game.getStatus())
                .playerHand(game.getPlayerHand())
                .croupierHand(game.getStatus() == GameStatus.FINISHED ? game.getCroupierHand()
                        : game.getCroupierHand().subList(0, 1))
                .croupierVisibleCard(game.getStatus() != GameStatus.FINISHED ?
                        game.getCroupierHand().get(0) : null)
                .build();
    }

    private static PlayerPlayingDTO toPlayerPlayingDTO(PlayerPlaying playerPlaying) {
        return PlayerPlayingDTO.builder()
                .id(playerPlaying.getId())
                .name(playerPlaying.getName())
                .totalPoints(playerPlaying.getTotalPoints())
                .status(playerPlaying.getStatus())
                .build();
    }
}
