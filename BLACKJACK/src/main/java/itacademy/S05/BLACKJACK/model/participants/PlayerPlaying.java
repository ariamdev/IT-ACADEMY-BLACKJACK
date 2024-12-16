package itacademy.S05.BLACKJACK.model.participants;

import itacademy.S05.BLACKJACK.model.enums.PlayerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerPlaying {
        @Id
        private int id;
        private String name;
        private int totalPoints;
        private PlayerStatus status;
}
