package itacademy.S05.BLACKJACK;

import itacademy.S05.BLACKJACK.controller.PlayerController;
import itacademy.S05.BLACKJACK.model.participants.Player;
import itacademy.S05.BLACKJACK.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    private Player mockPlayer;
    private Player mockPlayer2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockPlayer = new Player();
        mockPlayer.setId(1);
        mockPlayer.setName("Test Player");
        mockPlayer.setTotalPoints(100);

        mockPlayer2 = new Player();
        mockPlayer2.setId(2);
        mockPlayer2.setName("Test Player 2");
        mockPlayer2.setTotalPoints(150);
    }

    @Test
    void testGetRanking() {
        Flux<Player> mockRanking = Flux.just(mockPlayer);

        when(playerService.getRanking()).thenReturn(mockRanking);

        ResponseEntity<Flux<Player>> response = playerController.getRanking();

        assertNotNull(response);
        assertTrue(response.getBody().collectList().block().size() > 0);
        assertEquals("Test Player", response.getBody().collectList().block().get(0).getName());

    }

    @Test
    void testUpdateName() {

        Player updatedPlayer = new Player();
        updatedPlayer.setId(1);
        updatedPlayer.setName("UpdatedName");
        updatedPlayer.setTotalPoints(100);

        Mono<Player> updatedPlayerMono = Mono.just(updatedPlayer);

        when(playerService.updateName(1, "UpdatedName")).thenReturn(updatedPlayerMono);

        ResponseEntity<Mono<Player>> response = playerController.updateName(1, "UpdatedName");

        assertNotNull(response);
        assertEquals("UpdatedName", response.getBody().block().getName());
    }
}

