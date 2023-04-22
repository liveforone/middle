package middle.recommendservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private long impression;

    @Builder
    public Recommend(Long id, Long shopId, String username, long impression) {
        this.id = id;
        this.shopId = shopId;
        this.username = username;
        this.impression = impression;
    }

    public void createRecommend(Long shopId, String username) {
        this.shopId = shopId;
        this.username = username;
    }

    public void increaseImpression() {
        this.impression += 1;
    }

    public void decreaseImpression() {
        this.impression -= 1;
    }
}
