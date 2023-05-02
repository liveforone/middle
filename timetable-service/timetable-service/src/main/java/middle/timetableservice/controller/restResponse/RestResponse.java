package middle.timetableservice.controller.restResponse;

import org.springframework.http.ResponseEntity;

public class RestResponse {

    public static ResponseEntity<?> createTimetableSuccess() {
        return ResponseEntity
                .status(ResponseMessage.CREATE_TIMETABLE_SUCCESS.getStatus())
                .body(ResponseMessage.CREATE_TIMETABLE_SUCCESS.getValue());
    }

    public static ResponseEntity<?> updateTimeSuccess() {
        return ResponseEntity
                .ok(ResponseMessage.UPDATE_TIME_SUCCESS.getValue());
    }
}
