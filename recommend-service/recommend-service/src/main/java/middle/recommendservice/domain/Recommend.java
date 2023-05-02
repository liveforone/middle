package middle.recommendservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import middle.recommendservice.domain.constant.RecommendConstant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long shopId;

    @Column(unique = true, nullable = false)
    private String username;

    private long impression;

    private Recommend(Long shopId, String username, long impression) {
        this.shopId = shopId;
        this.username = username;
        this.impression = impression;
    }

    public static Recommend create(Long shopId, String username) {
        return new Recommend(shopId, username, RecommendConstant.ZERO);
    }

    public void increaseImpression(long inputImpression) {
        this.impression += Math.max(inputImpression, RecommendConstant.DEFAULT_IMPRESSION);
    }

    public void decreaseImpression() {
        this.impression -= RecommendConstant.ONE_IMPRESSION;
    }
}
