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
    @Transactional
    void minusRemainingTest() {
        //given
        /*
        * 기본적인 예약가능자수는 0이 될수 없다.
        * 따라서 1로 초기화해주고 한번 마이너스하여 0을 만들고
        * 두번째 마이너스 후에 boolean 값을 체크한다.
         */
        Long shopId = 99999L;
        String username = "dlffeffjoewjfewffwfwnlanw";
        long reservationHour = 12;
        long basicRemaining = 1;
        TimetableRequest timetableRequest = TimetableRequest.builder()
                .reservationHour(reservationHour)
                .basicRemaining(basicRemaining)
                .build();
        Long id = createTimetable(shopId, username, timetableRequest);
        em.flush();
        em.clear();

        //when
        timetableService.minusRemaining(id);  //0만들기
        boolean finalResult = timetableService.minusRemaining(id);

        //then
        Assertions
                .assertThat(finalResult)
                .isEqualTo(false);
    }
}