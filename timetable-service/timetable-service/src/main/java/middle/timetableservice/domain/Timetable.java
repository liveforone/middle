package middle.timetableservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long shopId;

    @Column(nullable = false)
    private String username;
    
    private long reservationTime;
    private long reservationMinute;
    private long remaining;

    @Builder
    public Timetable(Long id, Long shopId, String username, long reservationTime, long reservationMinute, long remaining) {
        this.id = id;
        this.shopId = shopId;
        this.username = username;
        this.reservationTime = reservationTime;
        this.reservationMinute = reservationMinute;
        this.remaining = remaining;
    }


}
