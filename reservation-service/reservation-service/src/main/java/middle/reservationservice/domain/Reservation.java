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

    @Enumerated(EnumType.STRING)
    private ReservationState reservationState;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdDate;

    private Reservation(Long timetableId, String username, ReservationState reservationState) {
        this.timetableId = timetableId;
        this.username = username;
        this.reservationState = reservationState;
    }

    public static Reservation create(Long timetableId, String username) {
        return new Reservation(timetableId, username, ReservationState.RESERVATION);
    }

    public void cancel() {
        this.reservationState = ReservationState.CANCEL;
    }
}
