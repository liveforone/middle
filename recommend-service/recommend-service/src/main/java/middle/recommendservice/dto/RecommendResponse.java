package middle.recommendservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecommendResponse {

    private Long id;
    private Long shopId;
    private long impression;
}
