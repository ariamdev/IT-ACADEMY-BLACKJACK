package itacademy.S05.BLACKJACK.model.DTO;

import itacademy.S05.BLACKJACK.model.cards.Card;
import itacademy.S05.BLACKJACK.model.enums.GameStatus;
import itacademy.S05.BLACKJACK.model.participants.Croupier;
import itacademy.S05.BLACKJACK.model.participants.PlayerPlaying;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameDTO implements Serializable {
    private String id;
    private PlayerPlayingDTO playerPlaying;
    private CroupierDTO croupier;
    private GameStatus status;
    private List<Card> playerHand;
    private List<Card> croupierHand;
    private Card croupierVisibleCard; //Para que muestre solo una carta al inicio del juego.
}
