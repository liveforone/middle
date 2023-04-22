package middle.recommendservice.service;

import lombok.RequiredArgsConstructor;
import middle.recommendservice.domain.Recommend;
import middle.recommendservice.repository.RecommendRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendService {

    private final RecommendRepository recommendRepository;

    @Transactional
    public void createRecommend(Long shopId, String username) {
        Recommend recommend = Recommend.builder().build();
        recommend.createRecommend(shopId, username);
        recommendRepository.save(recommend);
    }
}
