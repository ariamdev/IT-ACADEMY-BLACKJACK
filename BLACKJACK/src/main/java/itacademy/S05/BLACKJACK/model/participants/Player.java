package itacademy.S05.BLACKJACK.model.participants;

import itacademy.S05.BLACKJACK.model.cards.Card;
import itacademy.S05.BLACKJACK.model.enums.PlayerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("player")

//MYSQL no se updatean los puntos finales.
public class Player {
    @Id
    private int id;
    @Column("player_name")
    private String name;
    @Column("total_points")
    private int totalPoints;
    @Column("status") //Borrar columna status. Solo dejar player y points.
    private PlayerStatus status;
}
