package middle.recommendservice.service;

import jakarta.persistence.EntityManager;
import middle.recommendservice.dto.ImpressionRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class RecommendServiceTest {

    @Autowired
    RecommendService recommendService;

    @Autowired
    EntityManager em;

    void createRecommend(Long shopId, String username) {
        recommendService.createRecommend(shopId, username);
        em.flush();
        em.clear();
    }

    @Test
    @Transactional
    void createRecommendTest() {
        //given
        Long shopId = 100L;
        String username = "dlfjlsafjlesdffesfefwfwjfaf";

        //when
        recommendService.createRecommend(shopId, username);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(recommendService.getRecommendByUsername(username).getShopId())
                .isEqualTo(shopId);
    }

    @Test
    @Transactional
    void increaseImpressionTest() {
        //given
        Long shopId = 101L;
        String username = "dslfjewofjwljfewofjwofj";
        createRecommend(shopId, username);

        //when
        long inputImpression = 50;
        ImpressionRequest request = new ImpressionRequest();
        request.setImpression(inputImpression);
        recommendService.increaseImpression(request, username);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(recommendService.getRecommendByUsername(username).getImpression())
                .isEqualTo(inputImpression);
    }

    @Test
    @Transactional
    void decreaseImpressionTest() {
        //given
        Long shopId = 102L;
        String username = "dslfjewofjwljfewofjwofj";
        createRecommend(shopId, username);

        long inputImpression = 50;
        ImpressionRequest request = new ImpressionRequest();
        request.setImpression(inputImpression);
        recommendService.increaseImpression(request, username);
        em.flush();
        em.clear();

        //when
        recommendService.decreaseImpression(shopId);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(recommendService.getRecommendByUsername(username).getImpression())
                .isEqualTo(inputImpression - 1);
    }

}