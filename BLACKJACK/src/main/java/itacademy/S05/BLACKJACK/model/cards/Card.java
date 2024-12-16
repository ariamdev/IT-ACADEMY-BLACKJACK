package itacademy.S05.BLACKJACK.model.cards;


import itacademy.S05.BLACKJACK.model.enums.Suit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Card {
    private Suit suit; // Palo: Picas, Diamantes, Corazones y Tréboles
    //Cuantas cartas hay de cada palo y valor
    private int value;
    private String figure; // Ej: ACE o no ACE (ases) = 1 o 11 segun resultados
}
