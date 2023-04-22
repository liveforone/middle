package middle.recommendservice.algorithm;

import lombok.RequiredArgsConstructor;
import middle.recommendservice.repository.RecommendRepository;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class RecommendAlgorithm {

    private final RecommendRepository recommendRepository;

    public Long recommendShop() {
        Long sizeOfRecommend = recommendRepository.countSizeOfRecommend();
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        return Integer.toUnsignedLong(random.nextInt(Math.toIntExact(sizeOfRecommend)) + 1);
    }
}
