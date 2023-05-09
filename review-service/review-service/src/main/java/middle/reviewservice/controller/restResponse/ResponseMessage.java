package middle.reviewservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    CREATE_REVIEW_SUCCESS(201, "리뷰를 성공적으로 등록했습니다.");

    private final int status;
    private final String value;
}
