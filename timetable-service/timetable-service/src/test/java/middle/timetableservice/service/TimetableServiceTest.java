package middle.timetableservice.service;

import jakarta.persistence.EntityManager;
import middle.timetableservice.dto.TimetableRequest;
import middle.timetableservice.dto.UpdateTimeRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class TimetableServiceTest {

    @Autowired
    TimetableService timetableService;

    @Autowired
    EntityManager em;

    Long createTimetable(Long shopId, String username, TimetableRequest request) {
        return timetableService.createTimetable(shopId, username, request);
    }

    @Test
    @Transactional
    void createTimetableTest() {
        //given
        /*
        * minute 없이 생성한다.
        * remaining 이 정상 매핑 되는지도 확인한다.
         */
        Long shopId = 999L;
        String username = "dlfjoewjfwnlanw";
        long reservationHour = 12;
        long basicRemaining = 3;
        TimetableRequest timetableRequest = TimetableRequest.builder()
                .reservationHour(reservationHour)
                .basicRemaining(basicRemaining)
                .build();

        //when
        Long id = timetableService.createTimetable(shopId, username, timetableRequest);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(timetableService.getTimetableById(id).getRemaining())
                .isEqualTo(basicRemaining);
    }

    @Test
    @Transactional
    void updateTimeTest() {
        //given
        Long shopId = 9999L;
        String username = "dlffeffjoewjfwnlanw";
        long reservationHour = 12;
        long basicRemaining = 3;
        TimetableRequest timetableRequest = TimetableRequest.builder()
                .reservationHour(reservationHour)
                .basicRemaining(basicRemaining)
                .build();
        Long id = createTimetable(shopId, username, timetableRequest);
        em.flush();
        em.clear();

        //when
        long updatedMinute = 30;
        UpdateTimeRequest request = new UpdateTimeRequest();
        request.setReservationMinute(updatedMinute);
        timetableService.updateTime(request, id);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(timetableService.getTimetableById(id).getReservationMinute())
                .isEqualTo(updatedMinute);
        Assertions
                .assertThat(timetableService.getTimetableById(id).getReservationHour())
                .isEqualTo(reservationHour);
    }

    @Test
    void minusRemaining() {
    }
}