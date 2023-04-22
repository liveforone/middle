package middle.recommendservice.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImpressionRequest {

    @Positive(message = "추가하려는 노출횟수를 반드시 기입해주세요.")
    private long impression;
}
