package itacademy.S05.BLACKJACK.exception.custom;

public class DeckEmptyException extends RuntimeException {
    public DeckEmptyException(String message){
        super(message);
    }
}
