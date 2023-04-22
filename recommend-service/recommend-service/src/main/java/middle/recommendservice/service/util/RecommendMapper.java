package middle.recommendservice.service.util;

import middle.recommendservice.domain.Recommend;
import middle.recommendservice.dto.RecommendResponse;

public class RecommendMapper {

    public static RecommendResponse entityToDto(Recommend recommend) {
        return RecommendResponse.builder()
                .id(recommend.getId())
                .shopId(recommend.getShopId())
                .impression(recommend.getImpression())
                .build();
    }
}
