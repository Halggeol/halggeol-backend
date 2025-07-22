package com.halggeol.backend.recommend.service;


import com.halggeol.backend.domain.Fund;
import com.halggeol.backend.recommend.mapper.RecommendMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendMapper mapper;

    //user table의 algo_code 와 각 상품 table의 algo_code를 비교하여 가장 비슷한 5개를 뽑아낸다.
//    public ResponseEntity<?> recommend() {
//        return null;
//    }

    //content 기반 추천 알고리즘을 사용하여 상품의 algo_code를 업데이트 한다.
    //상품의 algo_code는 상품의 종류, 수익률, 위험도, 투자 기간 등 종합적으로 고려한다.
    //algo_code 는 0~100 까지의 float 값으로 표현된다.
    //algo_code가 높은 상품일수록 high risk high return 상품이다.
    //algo_code가 낮은 상품일수록 low risk low return 상품이다.
    //algo_code를 구하는 로직은 다음과 같다.
    //1. 상품의 종류에 따라 가중치를 부여한다.
    //2. 상품의 수익률에 따라 가중치를 부여한다.
    //3. 상품의 위험도에 따라 가중치를 부여한다.
    //4. 상품의 투자 기간에 따라 가중치를 부여한다.
    //5. 가중치를 종합하여 algo_code를 구한다.
    //단, 상품의 위험도는 총 6단계로 나누어져 있으며, 각 단계에 따라 algo_code의 상한값이 있다.
    //1단계 0~20, 2단계 20~40, 3단계 40~60, 4단계 60~80, 5단계 80~90, 6단계 90~100
    public void contentBasedFilteringProducts() {
        // TODO: 상품의 algo_code를 업데이트 하는 로직을 구현한다.
//        List<Fund> fundList = mapper.getFundList();
//        for (Fund fund : fundList) {
//            // 상품의 종류, 수익률, 위험도, 투자 기간 등을 기반으로 algo_code를 계산하는 로직을 구현한다.
//            // 예시: fund.setAlgoCode(calculatedAlgoCode);
//            // 이 부분은 실제 로직에 따라 구현해야 합니다.
//            System.out.println("Updating algo_code for fund: " + fund.getName());
//        }
    }


    //content 기반 추천 알고리즘을 사용하여 사용자의 algo_code를 업데이트 한다.
    //사용자의 algo_code는 사용자의 투자 성향, 투자 기간, 투자 금액 등 종합적으로 고려한다.
    public void contentBasendFilteringUsers() {

    }

//    public ResponseEntity<?> getRegretRecommendTop5() {
//        List<RegretRankingResponseDto> regretRanking = mapper.getRegretRanking();
//        if (regretRanking.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(regretRanking);
//    }
}
