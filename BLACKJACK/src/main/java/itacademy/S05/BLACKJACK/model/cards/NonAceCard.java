package itacademy.S05.BLACKJACK.model.cards;

import itacademy.S05.BLACKJACK.model.enums.Suit;
import lombok.Data;


@Data
public class NonAceCard extends Card{
    public NonAceCard (Suit suit, int value, String figure) {
        super(suit, value, figure);
    }
}
