package com.halggeol.backend.recommend.mapper;

import com.halggeol.backend.recommend.dto.RecommendResponseDTO;
import com.halggeol.backend.security.domain.User;
import com.halggeol.backend.recommend.dto.DepositAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.ForexAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.FundAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.PensionAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.ProductVectorResponseDTO;
import com.halggeol.backend.recommend.dto.SavingsAlgorithmResponseDTO;
import com.halggeol.backend.recommend.dto.UserVectorResponseDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecommendMapper {

//    List<Fund> getFundList();
    List<DepositAlgorithmResponseDTO> getDepositAlgorithmDetail();
    List<SavingsAlgorithmResponseDTO> getSavingsAlgorithmDetail();
    List<FundAlgorithmResponseDTO> getFundAlgorithmDetail();
    List<PensionAlgorithmResponseDTO> getPensionAlgorithmDetail();
    List<ForexAlgorithmResponseDTO> getForexAlgorithmDetail();
    List<ProductVectorResponseDTO> getProductVectors();
    List<UserVectorResponseDTO> getUserVectors();

    ProductVectorResponseDTO getProductVectorById(@Param("id") String productId);
    User getUserById(@Param("userId") Integer userId);

    int updateDepositAlgoCodeById(@Param("pid") String productId,
        @Param("algoCode") double algoCode,
        @Param("riskScore") double riskScore,
        @Param("yieldScore") double yieldScore,
        @Param("costScore") double costScore,
        @Param("liquidityScore") double liquidityScore,
        @Param("complexityScore") double complexityScore);
    int updateSavingsAlgoCodeById(@Param("pid") String productId,
        @Param("algoCode") double algoCode,
        @Param("riskScore") double riskScore,
        @Param("yieldScore") double yieldScore,
        @Param("costScore") double costScore,
        @Param("liquidityScore") double liquidityScore,
        @Param("complexityScore") double complexityScore);

    int updateFundAlgoCodeById(@Param("pid") String productId,
        @Param("algoCode") double algoCode,
        @Param("riskScore") double riskScore,
        @Param("yieldScore") double yieldScore,
        @Param("costScore") double costScore,
        @Param("liquidityScore") double liquidityScore,
        @Param("complexityScore") double complexityScore);
    int updatePensionAlgoCodeById(@Param("pid") String productId,
        @Param("algoCode") double algoCode,
        @Param("riskScore") double riskScore,
        @Param("yieldScore") double yieldScore,
        @Param("costScore") double costScore,
        @Param("liquidityScore") double liquidityScore,
        @Param("complexityScore") double complexityScore);
    int updateForexAlgoCodeById(@Param("pid") String productId,
        @Param("algoCode") double algoCode,
        @Param("riskScore") double riskScore,
        @Param("yieldScore") double yieldScore,
        @Param("costScore") double costScore,
        @Param("liquidityScore") double liquidityScore,
        @Param("complexityScore") double complexityScore);

    int saveRecommendations(@Param("userId") Integer userId,
        @Param("product1Id") String product1Id,
        @Param("product2Id") String product2Id,
        @Param("product3Id") String product3Id,
        @Param("product4Id") String product4Id,
        @Param("product5Id") String product5Id);


    List<RecommendResponseDTO> getRecommendationsByUserId(String userId);
}
