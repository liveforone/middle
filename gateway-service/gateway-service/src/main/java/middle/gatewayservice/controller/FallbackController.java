package middle.gatewayservice.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(FallbackUrl.BASE)
public class FallbackController {

    @GetMapping(FallbackUrl.USER)
    public Mono<String> fallbackUserGet() {
        return Mono.just(FallbackMessage.USER_LOG);
    }

    @PostMapping(FallbackUrl.USER)
    public Mono<String> fallbackUserPost() {
        return Mono.just(FallbackMessage.USER_LOG);
    }

    @PatchMapping(FallbackUrl.USER)
    public Mono<String> fallbackUserPatch() {
        return Mono.just(FallbackMessage.USER_LOG);
    }

    @DeleteMapping(FallbackUrl.USER)
    public Mono<String> fallbackUserDelete() {
        return Mono.just(FallbackMessage.USER_LOG);
    }

    @GetMapping(FallbackUrl.SHOP)
    public Mono<String> fallbackShopGet() {
        return Mono.just(FallbackMessage.SHOP_LOG);
    }

    @PostMapping(FallbackUrl.SHOP)
    public Mono<String> fallbackShopPost() {
        return Mono.just(FallbackMessage.SHOP_LOG);
    }

    @PutMapping(FallbackUrl.SHOP)
    public Mono<String> fallbackShopPut() {
        return Mono.just(FallbackMessage.SHOP_LOG);
    }

    @GetMapping(FallbackUrl.RECOMMEND)
    public Mono<String> fallbackRecommendGet() {
        return Mono.just(FallbackMessage.RECOMMEND_LOG);
    }

    @PostMapping(FallbackUrl.RECOMMEND)
    public Mono<String> fallbackRecommendPost() {
        return Mono.just(FallbackMessage.RECOMMEND_LOG);
    }

    @PutMapping(FallbackUrl.RECOMMEND)
    public Mono<String> fallbackRecommendPut() {
        return Mono.just(FallbackMessage.RECOMMEND_LOG);
    }

    @GetMapping(FallbackUrl.TIMETABLE)
    public Mono<String> fallbackTimetableGet() {
        return Mono.just(FallbackMessage.TIMETABLE_LOG);
    }

    @PostMapping(FallbackUrl.TIMETABLE)
    public Mono<String> fallbackTimetablePost() {
        return Mono.just(FallbackMessage.TIMETABLE_LOG);
    }

    @PutMapping(FallbackUrl.TIMETABLE)
    public Mono<String> fallbackTimetablePut() {
        return Mono.just(FallbackMessage.TIMETABLE_LOG);
    }

    @DeleteMapping(FallbackUrl.TIMETABLE)
    public Mono<String> fallbackTimetableDelete() {
        return Mono.just(FallbackMessage.TIMETABLE_LOG);
    }

    @GetMapping(FallbackUrl.RESERVATION)
    public Mono<String> fallbackReservationGet() {
        return Mono.just(FallbackMessage.RESERVATION_LOG);
    }

    @PostMapping(FallbackUrl.RESERVATION)
    public Mono<String> fallbackReservationPost() {
        return Mono.just(FallbackMessage.RESERVATION_LOG);
    }

    @PutMapping(FallbackUrl.RESERVATION)
    public Mono<String> fallbackReservationPut() {
        return Mono.just(FallbackMessage.RESERVATION_LOG);
    }

    @GetMapping(FallbackUrl.REVIEW)
    public Mono<String> fallbackReviewGet() {
        return Mono.just(FallbackMessage.REVIEW_LOG);
    }

    @PostMapping(FallbackUrl.REVIEW)
    public Mono<String> fallbackReviewPost() {
        return Mono.just(FallbackMessage.REVIEW_LOG);
    }

    @PutMapping(FallbackUrl.REVIEW)
    public Mono<String> fallbackReviewPut() {
        return Mono.just(FallbackMessage.REVIEW_LOG);
    }

    @DeleteMapping(FallbackUrl.REVIEW)
    public Mono<String> fallbackReviewDelete() {
        return Mono.just(FallbackMessage.REVIEW_LOG);
    }
}
