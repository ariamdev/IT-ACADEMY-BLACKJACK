package itacademy.S05.BLACKJACK.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.S05.BLACKJACK.model.participants.Player;
import itacademy.S05.BLACKJACK.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Tag(name="Player Controller", description = "Endpoints related to Players")
public class PlayerController {

    private PlayerService playerService;

    @Operation(summary = "Ranking", description = "Ranking by player scores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of players ranking sorted by scores."),
            @ApiResponse(responseCode = "400", description = "Players not found in the ranking list.")
    })
    @GetMapping("/ranking")
    public ResponseEntity<Flux<Player>> getRanking(){
        Flux<Player> rankedPlayers = playerService.getRanking();
        return ResponseEntity.ok(rankedPlayers);
    }


    @Operation(description = "Update player name",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New player name",
                    required = true)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Info of the player with the updated name"),
            @ApiResponse(responseCode = "400", description = "Player not found")
    })
    @PutMapping("/player/{id}")
    public ResponseEntity<Mono<Player>> updateName(@Parameter(name = "id",
            description = "Unique identifier of the game") @PathVariable int id, @RequestBody String name){
        Mono<Player> updatedPlayer = playerService.updateName(id,name);
        return ResponseEntity.ok(updatedPlayer);
    }
}
