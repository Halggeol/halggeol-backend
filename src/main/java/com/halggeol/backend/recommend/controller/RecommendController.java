package com.halggeol.backend.recommend.controller;


import com.halggeol.backend.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//아니 생각해보니까 얘는 컨트롤러가 필요가 없네

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    @GetMapping
    public void recommend() {
        recommendService.updateRecommendation();
    }

    @GetMapping("/test")
    public String test() {
        recommendService.updateAlgoCode();
        return "test Success";
    }

//    @GetMapping("/regret-ranking")
//    public ResponseEntity<?> regretRanking() {
//        return recommendService.getRegretRecommendTop5();
//    }
}
