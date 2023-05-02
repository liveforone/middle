package middle.timetableservice.controller.restResponse;

import org.springframework.http.ResponseEntity;

public class RestResponse {

    public static ResponseEntity<?> createTimetableSuccess() {
        return ResponseEntity
                .status(ResponseMessage.CREATE_TIMETABLE_SUCCESS.getStatus())
                .body(ResponseMessage.CREATE_TIMETABLE_SUCCESS.getValue());
    }
}
