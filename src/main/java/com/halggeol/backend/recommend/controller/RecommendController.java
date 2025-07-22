package com.halggeol.backend.recommend.controller;


import com.halggeol.backend.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

//    @GetMapping("/recommend")
//    public void recommend() {
//        recommendService.recommend();
//    }

    @GetMapping("/test")
    public String test() {
        recommendService.contentBasedFilteringProducts();
        return "test Success";
    }

//    @GetMapping("/regret-ranking")
//    public ResponseEntity<?> regretRanking() {
//        return recommendService.getRegretRecommendTop5();
//    }
}
