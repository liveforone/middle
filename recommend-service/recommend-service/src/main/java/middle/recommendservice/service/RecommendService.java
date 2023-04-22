package middle.recommendservice.service;

import lombok.RequiredArgsConstructor;
import middle.recommendservice.domain.Recommend;
import middle.recommendservice.dto.RecommendResponse;
import middle.recommendservice.repository.RecommendRepository;
import middle.recommendservice.service.util.RecommendMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendService {

    private final RecommendRepository recommendRepository;

    public RecommendResponse getRecommendByUsername(String username) {
        Recommend recommend = recommendRepository.findOneByUsername(username);
        return RecommendMapper.entityToDto(recommend);
    }

    @Transactional
    public void createRecommend(Long shopId, String username) {
        Recommend recommend = Recommend.builder().build();
        recommend.createRecommend(shopId, username);
        recommendRepository.save(recommend);
    }
}
