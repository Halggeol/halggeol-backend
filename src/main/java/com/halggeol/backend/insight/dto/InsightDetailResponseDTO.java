package com.halggeol.backend.insight.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.halggeol.backend.recommend.service.RecommendServiceImpl.Recommendation;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InsightDetailResponseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate recDate; // 후회 날짜
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate anlzDate; // 분석 날짜
    private String id; // 상품 id
    private Double interestRate; // 최대 금리 또는 수익률
    private List<ProfitSimulationDTO> profits; // 상품 수익 시뮬레이션
    private Boolean isCompound; // 이자 계산 복리 여부
    private Long minLimit; // 최소 가입 금액
    private Long maxLimit; // 최대 가입 금액
    private Integer saveTerm; // 가입 기간
    // 공격형 상품 관련
    private Float TER; // 보수비용
    private Float upfrontFee; // 선취수수료
    // 외환 관련
    private String currency; //거래 가능 통화
    private List<ForexCompareDTO> forexInfo; // 외환 상품 수익 시뮬레이션

    // 회고 관련
    private Integer regretScore; // 후회지수
    private Integer missAmount; // 놓친 금액

    // 상품 정보 관련
    // 현재는 비동기 처리로 사용하는 중
    private String description; // ai 한 줄 요약, d.description
    private String advantage; // ai 장점, d.advantage
    private String disadvantage; // ai 단점, d.disadvantage

    // 후회 피드백 관련
    private String name; // 사용자 이름
    private Boolean isSurveyed; // 피드백 설문 여부
    private Boolean isRegretted; // 피드백 설문 후회 여부
    private Integer regrettedReason; // 피드백 설문 후회 이유(1~4)

    // 유사상품 추천 관련
    private List<Recommendation> similarProducts;
}
