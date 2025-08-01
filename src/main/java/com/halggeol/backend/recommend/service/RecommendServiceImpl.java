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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService{

    private final RecommendMapper mapper;

    private void updateStaticValues() {
        //ScoreCalculator의 static 변수를 초기화하는 메서드
        ScoreCalculator.MAX_RATE = mapper.getMaxRate();
        ScoreCalculator.MIN_RATE = mapper.getMinRate();
        ScoreCalculator.MAX_SAVE_TERM = mapper.getMaxSaveTerm();
        ScoreCalculator.MIN_SAVE_TERM = mapper.getMinSaveTerm();
        ScoreCalculator.MAX_FUND_PRICE_MOVEMENT = mapper.getMaxFundPriceMovement();
        ScoreCalculator.MIN_FUND_PRICE_MOVEMENT = mapper.getMinFundPriceMovement();
        ScoreCalculator.MAX_FUND_TER = mapper.getMaxFundTER();
        ScoreCalculator.MIN_FUND_TER = mapper.getMinFundTER();
    }

    @Override
    @Scheduled(cron = "0 0 0 1 * *")
    public void updateAlgoCode() {
        System.out.println("Updating algorithm codes for all products...");
        updateStaticValues(); //최대/최소 금리를 업데이트
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
    @Scheduled(cron = "15 0 0 * * *")
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

    @Override
    public void updateRecommendationByUserId(String userId) {
        //특정 유저의 추천 상품을 업데이트하는 로직
        List<UserVectorResponseDTO> userVectors = mapper.getUserVectors();
        //유저 벡터를 가져옴
        UserVectorResponseDTO userVector = null;
        for (UserVectorResponseDTO vector : userVectors) {
            if (vector.getId().equals(userId)) {
                userVector = vector;
                break;
            }
        }
        if (userVector == null) {
            System.out.println("User vector not found for user ID: " + userId);
            return; //유저 벡터가 없는 경우
        }
        List<Double> userVectorList = List.of(userVector.getYieldScore(), userVector.getRiskScore(),
            userVector.getCostScore(), userVector.getLiquidityScore(), userVector.getComplexityScore());

        List<ProductVectorResponseDTO> productVectors = mapper.getProductVectors();
        List<Recommendation> recommendations = recommendTop5(productVectors, userVectorList);
        System.out.println("Top 5 Recommendations for User ID: " + userId);
        for (Recommendation recommendation : recommendations) {
            System.out.println("Product: " + recommendation.dto().getId() + ", Score: " + recommendation.score());
        }
        mapper.saveRecommendations(Integer.parseInt(userId), recommendations.get(0).dto.getId(),recommendations.get(1).dto.getId(),
            recommendations.get(2).dto.getId(), recommendations.get(3).dto.getId(), recommendations.get(4).dto.getId());
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

    @Override
    public List<Recommendation> getSimilarProducts(String productId) {
        //상품 벡터와 유사한 상품을 찾는 로직
        ProductVectorResponseDTO productVector = mapper.getProductVectorById(productId);
        List<Double> productVectorList = List.of(productVector.getYieldScore(), productVector.getRiskScore(),
            productVector.getCostScore(), productVector.getLiquidityScore(), productVector.getComplexityScore());
        List<ProductVectorResponseDTO> productVectors = mapper.getProductVectors();
        return productVectors.stream()
            .map(dto -> new Recommendation(dto, cosineSimilarity(List.of(dto.getYieldScore(),dto.getRiskScore(),dto.getCostScore(),
                dto.getLiquidityScore(),dto.getComplexityScore()), productVectorList)))
            .sorted(Comparator.comparingDouble(Recommendation::score).reversed())
            .limit(5)
            .toList();
    }

    @Override
    public List<RecommendResponseDTO> getRecommendProducts(String userId) {
        //유저 ID로 추천 상품을 가져오는 로직
        List<RecommendResponseDTO> recommendations = mapper.getRecommendationsByUserId(userId);
        if (recommendations.isEmpty()) {
            return List.of(); //추천 상품이 없는 경우 빈 리스트 반환
        }
        return recommendations;
    }
    public record Recommendation(ProductVectorResponseDTO dto, double score){}

    @Override
    public Double getProductMatchScore(String productId, String userId) {
        //상품 ID와 유저 ID로 추천 상품의 매칭 점수를 가져오는 로직
        ProductVectorResponseDTO productVector = mapper.getProductVectorById(productId);
        List<UserVectorResponseDTO> userVectors = mapper.getUserVectors();
        UserVectorResponseDTO userVector = userVectors.stream()
            .filter(u -> u.getId().equals(userId))
            .findFirst()
            .orElse(null);
        // 유사도 계산
        if (productVector != null && userVector != null) {
            List<Double> productVectorList = List.of(productVector.getYieldScore(), productVector.getRiskScore(),
                productVector.getCostScore(), productVector.getLiquidityScore(), productVector.getComplexityScore());
            List<Double> userVectorList = List.of(userVector.getYieldScore(), userVector.getRiskScore(),
                userVector.getCostScore(), userVector.getLiquidityScore(), userVector.getComplexityScore());
            return cosineSimilarity(productVectorList, userVectorList);
        }
        return null; //해당 상품이 추천 목록에 없는 경우 null 반환
    }

}
