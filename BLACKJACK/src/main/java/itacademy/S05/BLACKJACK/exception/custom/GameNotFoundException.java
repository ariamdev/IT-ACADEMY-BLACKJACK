package itacademy.S05.BLACKJACK.exception.custom;

public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(String message){
        super(message);
    }
}
