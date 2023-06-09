package middle.timetableservice.provideController;

import lombok.RequiredArgsConstructor;
import middle.timetableservice.service.TimetableService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import static middle.timetableservice.provideController.constant.ProvideParamToReservation.*;
import static middle.timetableservice.provideController.constant.ProvideUrlToReservation.*;

@RestController
@RequiredArgsConstructor
public class ProvideControllerToReservation {

    private final TimetableService timetableService;

    @PostMapping(GET_SHOP_ID)
    public Long getShopId(
            @PathVariable(ID) Long id
    ) {
        return timetableService.getShopIdById(id);
    }

    @PostMapping(RESERVE_TIMETABLE)
    public boolean reserveTimetable(
            @PathVariable(ID) Long id
    ) {
        return timetableService.minusRemaining(id);
    }

    @PutMapping(CANCEL_TIMETABLE)
    public boolean cancelTimetable(
            @PathVariable(ID) Long id
    ) {
        return timetableService.plusRemaining(id);
    }
}
