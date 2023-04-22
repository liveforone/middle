package middle.recommendservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.recommendservice.service.RecommendService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RecommendController {

    private final RecommendService recommendService;
}
