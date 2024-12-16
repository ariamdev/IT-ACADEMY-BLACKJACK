package itacademy.S05.BLACKJACK.repository;

import itacademy.S05.BLACKJACK.model.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface GameRepository extends ReactiveMongoRepository<Game, String> {
}
