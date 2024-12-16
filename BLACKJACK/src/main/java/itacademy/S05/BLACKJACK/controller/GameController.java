package itacademy.S05.BLACKJACK.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itacademy.S05.BLACKJACK.model.DTO.GameDTO;
import itacademy.S05.BLACKJACK.service.GameService;
import itacademy.S05.BLACKJACK.model.DTO.GameMapperDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
@Tag(name="Game Controller", description = "Endpoints related to BlackJack game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Operation(
            description = "Create New Game",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Name for the player",
                    required = true)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Details of the game created (cards received to player and croupier) and player info."),
            @ApiResponse(responseCode = "400", description = "Player could not be created.")
    })
    @PostMapping("/new")
    public Mono<ResponseEntity<GameDTO>> createGame(@RequestBody String playerName) {
        return gameService.createGame(playerName)
                .map(GameMapperDTO::toGameDTO)
                .map(gameDTO -> ResponseEntity.status(HttpStatus.CREATED).body(gameDTO));
    }

    @Operation(description = "Receive all the game details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game details"),
            @ApiResponse(responseCode = "404", description = "Game not found with the id")
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<GameDTO>> getGameInfo(@Parameter(name = "id",
            description = "Unique identifier of the game") @PathVariable String id){
        return gameService.getGameInfo(id)
                .map(GameMapperDTO::toGameDTO)
                .map(gameDTO -> ResponseEntity.status(HttpStatus.OK).body(gameDTO));
    }

    @Operation(description = "Make a move for the player (Hit/Stand)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Game move(Hit/Stand)",
            required = true)
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Details of the game movement and cards received."),
            @ApiResponse(responseCode = "400", description = "Invalid move type. Only 'HIT' or 'STAND' are allowed. Don't cheat!",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Game not found with the id.")
    })
    @PostMapping("/{id}/play")
    public Mono<ResponseEntity<GameDTO>> makeMove(@Parameter(name = "id",
            description = "Unique identifier of the game") @PathVariable String id, @RequestBody String moveType){
        return gameService.makeMove(id, moveType)
                .map(GameMapperDTO::toGameDTO)
                .map(gameDTO -> ResponseEntity.status(HttpStatus.OK).body(gameDTO));

    }

    @Operation(description = "Delete one game by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Game not found with the id")
    })
    @DeleteMapping("/{id}/delete")
    public Mono<ResponseEntity<Void>> deleteGame(@Parameter(name = "id",
            description = "Unique identifier of the game") @PathVariable String id){
        return gameService.deleteGame(id)
                .then(Mono.just(ResponseEntity.noContent().build()));

    }

}
