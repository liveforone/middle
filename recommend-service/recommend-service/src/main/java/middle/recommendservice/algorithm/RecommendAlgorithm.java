package middle.recommendservice.algorithm;

import lombok.RequiredArgsConstructor;
import middle.recommendservice.repository.RecommendRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommendAlgorithm {

    private final RecommendRepository recommendRepository;


}
