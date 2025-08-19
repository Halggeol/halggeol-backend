package com.halggeol.backend.recommend.service;



import static com.halggeol.backend.recommend.service.calculater.SimilarityCalculator.cosineSimilarity;

import com.halggeol.backend.recommend.dto.DepositAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.ForexAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.FundAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.PensionAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.ProductVectorResponseDTO;
import com.halggeol.backend.recommend.dto.ProductVectorUpdateResponseDTO;
import com.halggeol.backend.recommend.dto.RecommendResponseDTO;
import com.halggeol.backend.recommend.dto.SavingsAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.UserVectorResponseDTO;
import com.halggeol.backend.recommend.mapper.RecommendMapper;
import com.halggeol.backend.recommend.service.calculater.ScoreCalculator;
import com.halggeol.backend.survey.dto.TendencySurveyRequestDTO;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendServiceImpl implements RecommendService {

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
    @Scheduled(cron = "0 0 0 * * *")
    public void updateAlgoCode() {
        System.out.println("Updating algorithm codes for all products...");
        updateStaticValues(); //최대/최소 금리를 업데이트
        List<DepositAlgorithmResponseDTO> depositList = mapper.getDepositAlgorithmDetail();
        System.out.println("Deposit Algorithm Details: " + depositList.size());
        for(DepositAlgorithmResponseDTO deposit : depositList) {
            ProductVectorUpdateResponseDTO responseDTO = ScoreCalculator.calculate("deposit", deposit);
            mapper.updateProductVectorById(responseDTO); // 0~100으로 변환
        }
        List<SavingsAlgorithmResponseDTO> savingsList = mapper.getSavingsAlgorithmDetail();
        System.out.println("Savings Algorithm Details: " + savingsList.size());
        for(SavingsAlgorithmResponseDTO savings : savingsList) {
            ProductVectorUpdateResponseDTO responseDTO = ScoreCalculator.calculate("savings", savings);
            mapper.updateProductVectorById(responseDTO);
            }
        List<FundAlgorithmResponseDTO> fundList = mapper.getFundAlgorithmDetail();
        System.out.println("Fund Algorithm Details: " + fundList.size());
        for (FundAlgorithmResponseDTO fund : fundList) {
            ProductVectorUpdateResponseDTO responseDTO = ScoreCalculator.calculate("fund", fund);
            mapper.updateProductVectorById(responseDTO);
        }
        List<PensionAlgorithmResponseDTO> pensionList = mapper.getPensionAlgorithmDetail();
        System.out.println("Pension Algorithm Details: " + pensionList.size());
        for (PensionAlgorithmResponseDTO pension : pensionList) {
            ProductVectorUpdateResponseDTO responseDTO = ScoreCalculator.calculate("pension", pension);
            mapper.updateProductVectorById(responseDTO);
        }
        List<ForexAlgorithmResponseDTO> forexList = mapper.getForexAlgorithmDetail();
        System.out.println("Forex Algorithm Details: " + forexList.size());
        for( ForexAlgorithmResponseDTO forex : forexList) {
            ProductVectorUpdateResponseDTO responseDTO = ScoreCalculator.calculate("forex", forex);
            mapper.updateProductVectorById(responseDTO);
        }
        System.out.println("All algorithm codes updated successfully.");
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void updateRecommendation() {
        System.out.println("Updating recommendations for all users...");
        List<UserVectorResponseDTO> userVectors = mapper.getUserVectors(); //유저 벡터 리스트를 가져옴
        List<ProductVectorResponseDTO> productVectors = mapper.getProductVectors(); //상품 벡터 리스트를 가져옴
        for (UserVectorResponseDTO userVector : userVectors) {
            CronExpression cron = CronExpression.parse(mapper.getUserById(Integer.parseInt(userVector.getId())).getInsightCycle());
            //cron 표현식이 현재 시간과 일치하는지 확인
            ZonedDateTime now = ZonedDateTime.now();

            ZonedDateTime nextExecution = cron.next(now.minusWeeks(1));
            if (nextExecution == null || nextExecution.isBefore(now)) {
                System.out.println("Skipping recommendation update for user ID: " + userVector.getId() +
                    " as the cron expression does not match current time.");
                continue; //현재 시간과 일치하지 않는 경우 건너뜀
            }
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
    public void updateRecommendationByEmail(String userId) {
        //특정 유저의 추천 상품을 업데이트하는 로직
        UserVectorResponseDTO userVector = mapper.getUserVectorByEmail(userId);
        //유저 벡터를 가져옴
        if (userVector == null) {
            System.out.println("User vector not found for user ID: " + userId);
            return; //유저 벡터가 없는 경우
        }
        List<Double> userVectorList = List.of(userVector.getYieldScore(), userVector.getRiskScore(),
            userVector.getCostScore(), userVector.getLiquidityScore(), userVector.getComplexityScore());

        List<ProductVectorResponseDTO> productVectors = mapper.getProductVectors();
        List<Recommendation> recommendations = recommendTop5(productVectors, userVectorList);
        int userid = mapper.findUserIdByEmail(userId);
        System.out.println("Top 5 Recommendations for User ID: " + userId);
        for (Recommendation recommendation : recommendations) {
            System.out.println("Product: " + recommendation.dto().getId() + ", Score: " + recommendation.score());
        }
        mapper.saveRecommendations(userid, recommendations.get(0).dto.getId(),recommendations.get(1).dto.getId(),
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
        // 자기 자신이 뜨면 제외 후 유사도를 계산하여 5개 추천
        return productVectors.stream()
            .filter(dto -> !dto.getId().equals(productId)) // 자기 자신 제외
            .map(dto -> new Recommendation(dto, cosineSimilarity(List.of(dto.getYieldScore(), dto.getRiskScore(),
                dto.getCostScore(), dto.getLiquidityScore(), dto.getComplexityScore()), productVectorList)))
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

        // 각 추천 상품에 대해 매칭 점수를 계산하여 설정
        for (RecommendResponseDTO recommendation : recommendations) {
            Double matchScore = getProductMatchScore(recommendation.getProductId(), userId);
//            Double matchScore = 0.5;
            if (matchScore != null) {
                recommendation.setMatchScore((int) Math.round(matchScore * 100)); // 0~100 퍼센트로 변환
            }
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

    public UserVectorResponseDTO initUserVector(TendencySurveyRequestDTO survey, int risk) {
        if(risk == 1) { // 가장 공격적 투자 성향
            return UserVectorResponseDTO.builder()
                .yieldScore(0.85)
                .riskScore(0.85)
                .costScore(0.5)
                .liquidityScore(0.35)
                .complexityScore(0.7)
                .build();
        } else if(risk == 2) {
            return UserVectorResponseDTO.builder()
                .yieldScore(0.7)
                .riskScore(0.65)
                .costScore(0.4)
                .liquidityScore(0.5)
                .complexityScore(0.5)
                .build();
        } else if(risk == 3) {
            return UserVectorResponseDTO.builder()
                .yieldScore(0.55)
                .riskScore(0.45)
                .costScore(0.35)
                .liquidityScore(0.65)
                .complexityScore(0.4)
                .build();
        } else if(risk == 4) {
            return UserVectorResponseDTO.builder()
                .yieldScore(0.45)
                .riskScore(0.25)
                .costScore(0.25)
                .liquidityScore(0.75)
                .complexityScore(0.25)
                .build();
        } else { // 가장 안전 추구 투자 성향
            return UserVectorResponseDTO.builder()
                .yieldScore(0.35)
                .riskScore(0.1)
                .costScore(0.2)
                .liquidityScore(0.85)
                .complexityScore(0.15)
                .build();
        }
    }
}
