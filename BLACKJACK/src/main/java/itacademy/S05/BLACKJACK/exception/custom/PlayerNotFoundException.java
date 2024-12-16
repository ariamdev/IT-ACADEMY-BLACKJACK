package itacademy.S05.BLACKJACK.exception.custom;

public class PlayerNotFoundException extends RuntimeException{
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
