package middle.reservationservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long timetableId;

    @Column(nullable = false, updatable = false)
    private String username;

    @Column(nullable = false, updatable = false)
    private Long shopId;

    @Enumerated(EnumType.STRING)
    private ReservationState reservationState;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdDate;

    private Reservation(Long timetableId, String username, Long shopId, ReservationState reservationState) {
        this.timetableId = timetableId;
        this.username = username;
        this.shopId = shopId;
        this.reservationState = reservationState;
    }

    public static Reservation create(Long timetableId, String username, Long shopId) {
        return new Reservation(timetableId, username, shopId, ReservationState.RESERVATION);
    }

    public void cancel() {
        this.reservationState = ReservationState.CANCEL;
    }
}
