package middle.timetableservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.timetableservice.controller.constant.ParamConstant;
import middle.timetableservice.controller.constant.TimetableUrl;
import middle.timetableservice.dto.TimetableResponse;
import middle.timetableservice.service.TimetableService;
import middle.timetableservice.validator.TimetableValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TimetableController {

    private final TimetableService timetableService;
    private final TimetableValidator timetableValidator;

    @GetMapping(TimetableUrl.TIMETABLE_PAGE_BY_SHOP)
    public ResponseEntity<?> timetablesPage(
            @PathVariable(ParamConstant.SHOP_ID) Long shopId,
            @RequestParam(name = ParamConstant.LAST_ID, defaultValue = ParamConstant.DEFAULT_ID) Long lastId
    ) {
        List<TimetableResponse> timetables = timetableService.getTimetablesByShopId(shopId, lastId);

        return ResponseEntity.ok(timetables);
    }

    @GetMapping(TimetableUrl.TIMETABLE_DETAIL)
    public ResponseEntity<?> timetableDetail(
            @PathVariable(ParamConstant.ID) Long id
    ) {
        timetableValidator.validateTimetableNull(id);

        TimetableResponse timetable = timetableService.getTimetableById(id);
        return ResponseEntity.ok(timetable);
    }


    //provide controller로 예약 가능자수 마이너스 처리 후 bool 값 리턴
    //등록시 권한검증 + 상점 검증(유저네임으로 상점 찾아서 id리턴 후 id랑 파라미터랑 검증하여 확인)
    //삭제
}
