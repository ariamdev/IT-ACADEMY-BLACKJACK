package itacademy.S05.BLACKJACK.model.DTO;

import itacademy.S05.BLACKJACK.model.enums.PlayerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CroupierDTO {
    private PlayerStatus status;
}
