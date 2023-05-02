package middle.timetableservice.provideController;

import lombok.RequiredArgsConstructor;
import middle.timetableservice.provideController.constant.ProvideParamToReservation;
import middle.timetableservice.provideController.constant.ProvideUrlToReservation;
import middle.timetableservice.service.TimetableService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProvideControllerToReservation {

    private final TimetableService timetableService;

    @PostMapping(ProvideUrlToReservation.MINUS_REMAINING)
    public boolean minusRemainingToReservation(
            @PathVariable(ProvideParamToReservation.ID) Long id
    ) {
        return timetableService.minusRemaining(id);
    }
}
