package itacademy.S05.BLACKJACK.service.impl;

import itacademy.S05.BLACKJACK.exception.custom.PlayerNotFoundException;
import itacademy.S05.BLACKJACK.model.participants.Player;
import itacademy.S05.BLACKJACK.repository.PlayerRepository;
import itacademy.S05.BLACKJACK.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Mono<Player> createPlayer(String name) {
        Player player = Player.builder()
                .name(name)
                .totalPoints(0)
                .build();

        return playerRepository.save(player);
    }

    @Override
    public Flux<Player> getRanking() {
        return playerRepository.findAll()
                .sort((p1, p2) -> Integer.compare(p2.getTotalPoints(), p1.getTotalPoints()))
                .switchIfEmpty(Flux.error(new PlayerNotFoundException("Players not found in the ranking list")));
    }

    @Override
    public Mono<Player> updateName(int id, String name) {
        return playerRepository.findById(id).
                switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found with id " + id)))
                .flatMap(player -> {
                player.setName(name);
            return playerRepository.save(player);
        });
    }

    public Mono<Player> updatePoints(int id, int points) {
        return playerRepository.findById(id)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found")))
                .flatMap(player -> {
                    player.setTotalPoints(points);
                    return playerRepository.save(player);
                });

    }

}
