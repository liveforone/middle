package middle.recommendservice.service;

import lombok.RequiredArgsConstructor;
import middle.recommendservice.async.AsyncConstant;
import middle.recommendservice.cache.CacheConstant;
import middle.recommendservice.domain.Recommend;
import middle.recommendservice.dto.ImpressionRequest;
import middle.recommendservice.dto.RecommendResponse;
import middle.recommendservice.repository.RecommendRepository;
import middle.recommendservice.service.util.RecommendMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Async;
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
    @CachePut(cacheNames = CacheConstant.COUNT_RECOMMEND_NAME, value = CacheConstant.COUNT_RECOMMEND)
    public void createRecommend(Long shopId, String username) {
        Recommend recommend = Recommend.builder().build();
        recommend.createRecommend(shopId, username);
        recommendRepository.save(recommend);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void increaseImpression(ImpressionRequest impressionRequest, String username) {
        Recommend recommend = recommendRepository.findOneByUsername(username);
        recommend.increaseImpression(impressionRequest.getImpression());
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void decreaseImpression(Long shopId) {
        Recommend recommend = recommendRepository.findOneByShopId(shopId);
        recommend.decreaseImpression();
    }
}
