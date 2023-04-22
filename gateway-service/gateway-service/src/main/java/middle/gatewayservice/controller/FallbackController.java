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
}
