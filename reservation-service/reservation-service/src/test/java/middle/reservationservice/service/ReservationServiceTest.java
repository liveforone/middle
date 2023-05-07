package middle.reservationservice.service;

import jakarta.persistence.EntityManager;
import middle.reservationservice.domain.ReservationState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void reserveTest() {
        //given
        Long timetableId = 999L;
        String username = "dslfojafewjfafnoveaf";
        Long shopId = 1111L;

        //when
        reservationService.reserve(timetableId, username, shopId);
        em.flush();
        em.clear();

        //then
        assertThat(reservationService.getReservationsByUsername(username, 0L).get(0).getTimetableId())
                .isEqualTo(timetableId);
    }

    @Test
    @Transactional
    void cancel() {
        //given
        Long timetableId = 9999L;
        String username = "fewfwfeddxxxxxxxxs";
        Long shopId = 11111L;
        reservationService.reserve(timetableId, username, shopId);
        em.flush();
        em.clear();

        //when
        Long id = reservationService.getReservationsByUsername(username, 0L).get(0).getId();
        reservationService.cancel(id);
        em.flush();
        em.clear();

        //then
        assertThat(reservationService.getReservationsByUsername(username, 0L).get(0).getReservationState())
                .isEqualTo(ReservationState.CANCEL);
    }
}