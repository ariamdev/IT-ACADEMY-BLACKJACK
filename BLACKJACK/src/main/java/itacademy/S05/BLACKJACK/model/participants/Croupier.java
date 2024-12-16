package itacademy.S05.BLACKJACK.model.participants;


import itacademy.S05.BLACKJACK.model.cards.Card;
import itacademy.S05.BLACKJACK.model.enums.PlayerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Croupier {
    private PlayerStatus status;
}
