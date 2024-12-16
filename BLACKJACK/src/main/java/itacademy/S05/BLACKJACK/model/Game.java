package itacademy.S05.BLACKJACK.model;

import itacademy.S05.BLACKJACK.model.cards.Card;
import itacademy.S05.BLACKJACK.model.enums.GameStatus;
import itacademy.S05.BLACKJACK.model.participants.Croupier;
import itacademy.S05.BLACKJACK.model.participants.PlayerPlaying;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "game") //Verificar que funciona bien para MONGODB
public class Game {
    @Id
    private String id;
    private PlayerPlaying playerPlaying;
    private Croupier croupier;
    private GameStatus status; // Status del juego: En curso o finalizado
    private List<Card> playerHand;
    private List<Card> croupierHand;
    private List<Card> deck;

}
