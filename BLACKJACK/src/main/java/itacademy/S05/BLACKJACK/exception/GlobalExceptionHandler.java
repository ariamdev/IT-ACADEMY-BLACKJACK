package itacademy.S05.BLACKJACK.exception;

import itacademy.S05.BLACKJACK.exception.custom.DeckEmptyException;
import itacademy.S05.BLACKJACK.exception.custom.GameNotFoundException;
import itacademy.S05.BLACKJACK.exception.custom.PlayerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    public Mono<ResponseEntity<String>> handlePlayerNotFound(PlayerNotFoundException e) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
    }

    @ExceptionHandler(DeckEmptyException.class)
    public Mono<ResponseEntity <String>> handleDeckEmpty(DeckEmptyException e) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()));
    }

    @ExceptionHandler(GameNotFoundException.class)
    public Mono<ResponseEntity <String>> handleGameNotFound(GameNotFoundException e) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
    }
}
