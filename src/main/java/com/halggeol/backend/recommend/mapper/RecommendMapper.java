package com.halggeol.backend.recommend.mapper;

import com.halggeol.backend.recommend.dto.ProductVectorUpdateResponseDTO;
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

    Double getMaxRate();
    Double getMinRate();
    Integer getMaxSaveTerm();
    Integer getMinSaveTerm();
    Double getMaxFundPriceMovement();
    Double getMinFundPriceMovement();
    Double getMaxFundTER();
    Double getMinFundTER();

    ProductVectorResponseDTO getProductVectorById(@Param("id") String productId);
    UserVectorResponseDTO getUserVectorById(@Param("id") Integer userId);
    UserVectorResponseDTO getUserVectorByEmail(@Param("email") String email);
    User getUserById(@Param("id") Integer userId);

    int updateProductVectorById(@Param("productVector") ProductVectorUpdateResponseDTO productVector);

    int saveRecommendations(@Param("userId") Integer userId,
        @Param("product1Id") String product1Id,
        @Param("product2Id") String product2Id,
        @Param("product3Id") String product3Id,
        @Param("product4Id") String product4Id,
        @Param("product5Id") String product5Id);


    List<RecommendResponseDTO> getRecommendationsByUserId(String userId);
}
