package middle.recommendservice.algorithm;

import lombok.RequiredArgsConstructor;
import middle.recommendservice.repository.RecommendRepository;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class RecommendAlgorithm {

    private final RecommendRepository recommendRepository;

    private final static int START_ID = 1;

    public Long recommendShop() {
        Long sizeOfRecommend = recommendRepository.countSizeOfRecommend();
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        return Integer.toUnsignedLong(random.nextInt(Math.toIntExact(sizeOfRecommend)) + START_ID);
    }
}
