package middle.reservationservice.feignClient;

import middle.reservationservice.feignClient.constant.TimetableParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static middle.reservationservice.feignClient.constant.TimetableParam.*;
import static middle.reservationservice.feignClient.constant.TimetableUrl.*;

@FeignClient(name = BASE)
public interface TimetableFeignService {
    @PostMapping(RESERVE_TIMETABLE)
    boolean reserveTimetable(@PathVariable(ID) Long timetableId);
}