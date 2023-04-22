package middle.recommendservice.service.util;

import middle.recommendservice.domain.Recommend;
import middle.recommendservice.dto.RecommendResponse;
import middle.recommendservice.utility.CommonUtils;

public class RecommendMapper {

    public static RecommendResponse entityToDto(Recommend recommend) {
        if (CommonUtils.isNull(recommend)) {
            return new RecommendResponse();
        }

        return RecommendResponse.builder()
                .id(recommend.getId())
                .shopId(recommend.getShopId())
                .impression(recommend.getImpression())
                .build();
    }
}
