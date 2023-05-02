package middle.timetableservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import middle.timetableservice.dto.TimetableRequest;
import middle.timetableservice.dto.UpdateTimeRequest;

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
    
    private long reservationHour;

    private long reservationMinute;

    @Column(updatable = false)
    private long basicRemaining;

    private long remaining;

    private Timetable(Long shopId, String username, long reservationHour, long reservationMinute, long basicRemaining, long remaining) {
        this.shopId = shopId;
        this.username = username;
        this.reservationHour = reservationHour;
        this.reservationMinute = reservationMinute;
        this.basicRemaining = basicRemaining;
        this.remaining = remaining;
    }

    public static Timetable create(Long shopId, String username, TimetableRequest timeTableRequest) {
        long remaining = timeTableRequest.getBasicRemaining();

        return new Timetable(
                shopId, username,
                timeTableRequest.getReservationHour(),
                timeTableRequest.getReservationMinute(),
                timeTableRequest.getBasicRemaining(),
                remaining
        );
    }

    public void updateTime(UpdateTimeRequest updateTimeRequest) {
        final long ZERO = 0;
        long reservationHour = updateTimeRequest.getReservationHour();
        long reservationMinute = updateTimeRequest.getReservationMinute();

        if (reservationHour != ZERO) {
            this.reservationHour = reservationHour;
        }

        if (reservationMinute != ZERO) {
            this.reservationMinute = reservationMinute;
        }
    }
}
