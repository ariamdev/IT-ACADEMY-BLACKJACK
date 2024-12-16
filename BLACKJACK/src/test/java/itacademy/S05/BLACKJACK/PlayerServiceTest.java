package itacademy.S05.BLACKJACK;

import itacademy.S05.BLACKJACK.exception.custom.PlayerNotFoundException;
import itacademy.S05.BLACKJACK.model.participants.Player;
import itacademy.S05.BLACKJACK.repository.PlayerRepository;
import itacademy.S05.BLACKJACK.service.impl.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    private Player mockPlayer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockPlayer = new Player();
        mockPlayer.setId(1);
        mockPlayer.setName("Random");
        mockPlayer.setTotalPoints(0);
    }

    @Test
    void testCreatePlayer() {
        doReturn(Mono.just(mockPlayer)).when(playerRepository).save(any(Player.class));

        Mono<Player> createdPlayer = playerService.createPlayer("Random");

        Player player = createdPlayer.block();
        assertNotNull(player);
        assertEquals("Random", player.getName());
        assertEquals(0, player.getTotalPoints());

        verify(playerRepository).save(any(Player.class));
    }


    @Test
    void testGetRanking() {
        Player player1 = new Player();
        player1.setName("Pepita");
        player1.setTotalPoints(150);

        Player player2 = new Player();
        player2.setName("Antonia");
        player2.setTotalPoints(100);

        Flux<Player> mockRanking = Flux.just(player1, player2);

        doReturn(mockRanking).when(playerRepository).findAll();

        Flux<Player> ranking = playerService.getRanking();

        assertNotNull(ranking);
        assertEquals(2, ranking.collectList().block().size());
        assertEquals("Pepita", ranking.collectList().block().get(0).getName());
        assertEquals("Antonia", ranking.collectList().block().get(1).getName());

        verify(playerRepository).findAll();
    }

    @Test
    void testGetRankingEmpty() {
        doReturn(Flux.empty()).when(playerRepository).findAll();

        assertThrows(PlayerNotFoundException.class, () -> {
            playerService.getRanking().blockFirst();
        });

        verify(playerRepository).findAll();
    }

    @Test
    void testUpdateName() {
        Player existingPlayer = new Player();
        existingPlayer.setId(1);
        existingPlayer.setName("Pepita");
        existingPlayer.setTotalPoints(100);

        Player updatedPlayer = new Player();
        updatedPlayer.setId(1);
        updatedPlayer.setName("Antonia");
        updatedPlayer.setTotalPoints(100);

        doReturn(Mono.just(existingPlayer)).when(playerRepository).findById(1);
        doReturn(Mono.just(updatedPlayer)).when(playerRepository).save(any(Player.class));

        Mono<Player> result = playerService.updateName(1, "Antonia");

        Player updated = result.block();

        assertNotNull(updated);
        assertEquals("Antonia", updated.getName());
        assertEquals(100, updated.getTotalPoints());

        verify(playerRepository).findById(1);
        verify(playerRepository).save(any(Player.class));
    }

    @Test
    void testUpdateNamePlayerNotFound() {

        doReturn(Mono.empty()).when(playerRepository).findById(1);

        assertThrows(PlayerNotFoundException.class, () -> {
            playerService.updateName(1, "Antonia").block();
        });

        verify(playerRepository).findById(1);
    }

    @Test
    void testUpdatePoints() {
        Player existingPlayer = new Player();
        existingPlayer.setId(1);
        existingPlayer.setName("Pepita");
        existingPlayer.setTotalPoints(100);

        Player updatedPlayer = new Player();
        updatedPlayer.setId(1);
        updatedPlayer.setName("Pepita");
        updatedPlayer.setTotalPoints(200);

        doReturn(Mono.just(existingPlayer)).when(playerRepository).findById(1);
        doReturn(Mono.just(updatedPlayer)).when(playerRepository).save(any(Player.class));

        Mono<Player> result = playerService.updatePoints(1, 200);

        Player updated = result.block();

        assertNotNull(updated);
        assertEquals(200, updated.getTotalPoints());

        verify(playerRepository, times(1)).findById(1);
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void testUpdatePointsPlayerNotFound() {

        doReturn(Mono.empty()).when(playerRepository).findById(1);

        assertThrows(PlayerNotFoundException.class, () -> {
            playerService.updatePoints(1, 200).block();
        });

        verify(playerRepository, times(1)).findById(1);
    }
}
