package com.halggeol.backend.recommend.service;



import static com.halggeol.backend.recommend.service.calculater.SimilarityCalculator.cosineSimilarity;

import com.halggeol.backend.recommend.dto.DepositAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.ForexAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.FundAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.PensionAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.ProductVectorResponseDTO;
import com.halggeol.backend.recommend.dto.RecommendResponseDTO;
import com.halggeol.backend.recommend.dto.SavingsAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.UserVectorResponseDTO;
import com.halggeol.backend.recommend.mapper.RecommendMapper;
import com.halggeol.backend.recommend.service.calculater.ScoreCalculator;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService{

    private final RecommendMapper mapper;

    @Override
    @Scheduled(cron = "0 0 0 * * *") // 매 분 실행
    public void updateAlgoCode() {
        System.out.println("Updating algorithm codes for all products...");
        List<DepositAlgorithmResponseDTO> depositList = mapper.getDepositAlgorithmDetail();
        for( DepositAlgorithmResponseDTO deposit : depositList) {
            Map<String, Double> scores = ScoreCalculator.calculateDepositScore(deposit);
            //이 score가 deposit의 algo_code로 사용될 예정
            System.out.println("Updating algo_code for deposit: " + deposit.getName() +
                " with score: " + scores.get("algoCode"));
            mapper.updateDepositAlgoCodeById(deposit.getId(), scores.get("algoCode")*100,scores.get("riskScore"), scores.get("yieldScore"), scores.get("costScore") , scores.get("liquidityScore"), scores.get("complexityScore")); // 0~100으로 변환
        }
        List<SavingsAlgorithmResponseDTO> savingsList = mapper.getSavingsAlgorithmDetail();
        for(SavingsAlgorithmResponseDTO savings : savingsList) {
            Map<String,Double> scores = ScoreCalculator.calculateSavingsScore(savings);
            //이 score가 savings의 algo_code로 사용될 예정
            System.out.println("Updating algo_code for savings: " + savings.getName() +
                " with score: " + scores.get("algoCode"));
            mapper.updateSavingsAlgoCodeById(savings.getId(), scores.get("algoCode")*100,scores.get("riskScore"), scores.get("yieldScore"), scores.get("costScore") , scores.get("liquidityScore"), scores.get("complexityScore")); // 0~100으로 변환
        }
        List<FundAlgorithmResponseDTO> fundList = mapper.getFundAlgorithmDetail();
        for (FundAlgorithmResponseDTO fund : fundList) {
            Map<String,Double> scores = ScoreCalculator.calculateFundScore(fund);
            //이 score가 fund의 algo_code로 사용될 예정
            System.out.println("Updating algo_code for fund: " + fund.getName() +
                " with score: " + scores.get("algoCode"));
            mapper.updateFundAlgoCodeById(fund.getId(), scores.get("algoCode")*100,scores.get("riskScore"), scores.get("yieldScore"), scores.get("costScore") , scores.get("liquidityScore"), scores.get("complexityScore")); // 0~100으로 변환
        }
        List<PensionAlgorithmResponseDTO> pensionList = mapper.getPensionAlgorithmDetail();
        for (PensionAlgorithmResponseDTO pension : pensionList) {
            Map<String, Double> scores = ScoreCalculator.calculatePensionScore(pension);
            //이 score가 pension의 algo_code로 사용될 예정
            System.out.println("Updating algo_code for pension: " + pension.getName() +
                " with score: " + scores.get("algoCode"));
            mapper.updatePensionAlgoCodeById(pension.getId(), scores.get("algoCode")*100,scores.get("riskScore"), scores.get("yieldScore"), scores.get("costScore") , scores.get("liquidityScore"), scores.get("complexityScore")); // 0~100으로 변환
        }

        List<ForexAlgorithmResponseDTO> forexList = mapper.getForexAlgorithmDetail();
        for( ForexAlgorithmResponseDTO forex : forexList) {
            Map<String, Double> scores = ScoreCalculator.calculateForexScore(forex);
            //이 score가 forex의 algo_code로 사용될 예정
            System.out.println("Updating algo_code for forex: " + forex.getName() +
                " with score: " + scores.get("algoCode"));
            mapper.updateForexAlgoCodeById(forex.getId(), scores.get("algoCode")*100,scores.get("riskScore"), scores.get("yieldScore"), scores.get("costScore") , scores.get("liquidityScore"), scores.get("complexityScore")); // 0~100으로 변환
        }

        System.out.println("All algorithm codes updated successfully.");
    }

    @Override
    @Scheduled(cron = "15 0 0 * * *") //
    public void updateRecommendation() {
        System.out.println("Updating recommendations for all users...");
        List<UserVectorResponseDTO> userVectors = mapper.getUserVectors(); //유저 벡터 리스트를 가져옴
        List<ProductVectorResponseDTO> productVectors = mapper.getProductVectors(); //상품 벡터 리스트를 가져옴
        for (UserVectorResponseDTO userVector : userVectors) {
            //유저 벡터를 리스트로 변환
            List<Double> userVectorList = List.of(userVector.getYieldScore(), userVector.getRiskScore(),
                userVector.getCostScore(), userVector.getLiquidityScore(), userVector.getComplexityScore());

            //상품 벡터와 유사한 상품을 추천
            List<Recommendation> recommendations = recommendTop5(productVectors, userVectorList);
            System.out.println("Top 5 Recommendations");
            for (Recommendation recommendation : recommendations) {
                System.out.println("Product: " + recommendation.dto().getId() + ", Score: " + recommendation.score());
            }
            //추천 결과를 DB에 저장
            mapper.saveRecommendations(Integer.parseInt(userVector.getId()), recommendations.get(0).dto.getId(),recommendations.get(1).dto.getId(),
                recommendations.get(2).dto.getId(), recommendations.get(3).dto.getId(), recommendations.get(4).dto.getId());
        }
    }


    private List<Recommendation> recommendTop5(List<ProductVectorResponseDTO> productVectors, List<Double> userVector) {
        //유저 성향 벡터와 가장 유사한 상품 벡터를 찾는 로직
        //여기서는 단순히 유사도 계산을 위해 코사인 유사도를 사용한다고 가정
        return productVectors.stream()
            .map(dto -> new Recommendation(dto, cosineSimilarity(List.of(dto.getYieldScore(),dto.getRiskScore(),dto.getCostScore(),
                dto.getLiquidityScore(),dto.getComplexityScore()), userVector)))
            .sorted(Comparator.comparingDouble(Recommendation::score).reversed())
            .limit(5)
            .toList();
    }

    public List<Recommendation> similarProducts(ProductVectorResponseDTO productVector, List<ProductVectorResponseDTO> productVectors) {
        //상품 벡터와 유사한 상품을 찾는 로직
        List<Double> productVectorList = List.of(productVector.getYieldScore(), productVector.getRiskScore(),
            productVector.getCostScore(), productVector.getLiquidityScore(), productVector.getComplexityScore());

        return productVectors.stream()
            .map(dto -> new Recommendation(dto, cosineSimilarity(List.of(dto.getYieldScore(),dto.getRiskScore(),dto.getCostScore(),
                dto.getLiquidityScore(),dto.getComplexityScore()), productVectorList)))
            .sorted(Comparator.comparingDouble(Recommendation::score).reversed())
            .limit(5)
            .toList();
    }

    public List<RecommendResponseDTO> getRecommendProducts(String userId) {
        //유저 ID로 추천 상품을 가져오는 로직
        List<RecommendResponseDTO> recommendations = mapper.getRecommendationsByUserId(userId);
        if (recommendations.isEmpty()) {
            return List.of(); //추천 상품이 없는 경우 빈 리스트 반환
        }
        return recommendations;
    }


    public record Recommendation(ProductVectorResponseDTO dto, double score){}
}
