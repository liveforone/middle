package middle.reservationservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import static middle.reservationservice.feignClient.constant.TimetableParam.*;
import static middle.reservationservice.feignClient.constant.TimetableUrl.*;

@FeignClient(name = BASE)
public interface TimetableFeignService {

    @PostMapping(GET_SHOP_ID)
    Long getShopId(@PathVariable(ID) Long timetableId);

    @PostMapping(RESERVE_TIMETABLE)
    boolean reserveTimetable(@PathVariable(ID) Long timetableId);

    @PutMapping(CANCEL_TIMETABLE)
    boolean cancelReservationTimetable(@PathVariable(ID) Long timetableId);
}
