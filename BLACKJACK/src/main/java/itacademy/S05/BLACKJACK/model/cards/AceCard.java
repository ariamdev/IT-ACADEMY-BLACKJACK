package itacademy.S05.BLACKJACK.model.cards;

import itacademy.S05.BLACKJACK.model.enums.Suit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
public class AceCard extends Card {

    public AceCard (Suit suit,int value, String figure) {
        super(suit, value, figure);
    }
}
