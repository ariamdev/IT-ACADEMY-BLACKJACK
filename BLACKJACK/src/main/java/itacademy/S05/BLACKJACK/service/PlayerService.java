package itacademy.S05.BLACKJACK.service;

import itacademy.S05.BLACKJACK.model.participants.Player;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface PlayerService {
    Mono<Player> createPlayer(String name);
    Flux<Player> getRanking();
    Mono<Player> updateName(int id, String name);
    Mono<Player> updatePoints(int id, int point);
}
