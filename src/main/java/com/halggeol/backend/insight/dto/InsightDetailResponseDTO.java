package com.halggeol.backend.insight.dto;
import java.sql.Date;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InsightDetailResponseDTO {
    private String recDate; // 후회 날짜
    private String anlzDate; // 분석 날짜
    private String id; // 상품 id
    private Double interestRate; // 최대 금리 또는 수익률
    private List<ProfitSimulationDTO> profits; // 상품 수익 시뮬레이션
    private Boolean isCompound; // 이자 계산 복리 여부
    private Long minLimit; // 최소 가입 금액
    private Long maxLimit; // 최대 가입 금액
    private Integer saveTerm; // 가입 기간
    private Float TER; // 보수비용
    private Float upfrontFee; // 선취수수료
//    private Double startExchange;// 추천일 기준 환율, 외환 전용
//    private Double endExchange;// 회고일 기준 환율, 외환 전용
    private Integer regretScore; // 후회지수
    private Integer missAmount; // 놓친 금액

    private String name; // 사용자 이름
    // 현재는 비동기 처리로 사용하는 중
    private String description; // ai 한 줄 요약, d.description
    private String advantage; // ai 장점, d.advantage
    private String disadvantage; // ai 단점, d.disadvantage

    private Boolean isSurveyed; // 피드백 설문 여부
    private Boolean isRegretted; // 피드백 설문 후회 여부
    private Integer regrettedReason; // 피드백 설문 후회 이유(1~4)

    // 유사상품 추천
}
