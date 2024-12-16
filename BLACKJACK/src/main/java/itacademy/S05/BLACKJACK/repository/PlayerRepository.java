package itacademy.S05.BLACKJACK.repository;

import itacademy.S05.BLACKJACK.model.participants.Player;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PlayerRepository extends ReactiveCrudRepository<Player, Integer> {
    Mono<Player> findById(int id);

}
