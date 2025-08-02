package com.halggeol.backend.products.service;

import static com.halggeol.backend.common.ProductPrefixHandler.handleProductByBiFunction;
import static com.halggeol.backend.common.ProductPrefixHandler.handleProductByConsumer;

import com.halggeol.backend.logs.service.LogService;
import com.halggeol.backend.products.dto.DepositDetailResponseDTO;
import com.halggeol.backend.products.dto.ForexDetailResponseDTO;
import com.halggeol.backend.products.dto.FundDetailResponseDTO;
import com.halggeol.backend.products.dto.PensionDetailResponseDTO;
import com.halggeol.backend.products.dto.SavingsDetailResponseDTO;
import com.halggeol.backend.products.mapper.ProductDetailMapper;
import com.halggeol.backend.recommend.service.RecommendService;
import com.halggeol.backend.security.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailMapper productDetailMapper;
    private final RecommendService recommendService;
    private final LogService logService;

    @Override
    @Transactional
    public Object getProductDetailById(String productId, @AuthenticationPrincipal CustomUser user) {

        String userId = String.valueOf(user.getUser().getId());
        // 조회수 증가 로직 먼저 호출 --> 비동기적으로 처리
        incrementProductViewCountAsync(productId);

        // 로그 처리
//        logService.buildLog("view", productId, Integer.valueOf(userId));

//        // productId의 첫 글자를 확인하여 상품 유형 확인
//        if (productId == null || productId.isEmpty()) {
//            throw new IllegalArgumentException("Product ID cannot be null or empty.");
//        }

        Object result = handleProductByBiFunction(
            productId,
            userId,
            productDetailMapper::selectDepositDetailById,
            productDetailMapper::selectSavingsDetailById,
            productDetailMapper::selectFundDetailById,
            productDetailMapper::selectForexDetailById,
            productDetailMapper::selectPensionDetailById);

            Double matchScore = recommendService.getProductMatchScore(productId, userId);
//            Double matchScore = 0.5;
            Integer scoreValue = null;
            
            if (matchScore != null) {
                // Double 값을 0-100 범위의 Integer로 변환 (코사인 유사도는 0-1 범위)
                scoreValue = (int) Math.round(matchScore * 100);
            }
            
            // 상품 타입에 따라 적절한 DTO로 캐스팅하여 score 설정
            if (result instanceof DepositDetailResponseDTO) {
                ((DepositDetailResponseDTO) result).setScore(scoreValue);
            } else if (result instanceof SavingsDetailResponseDTO) {
                ((SavingsDetailResponseDTO) result).setScore(scoreValue);
            } else if (result instanceof FundDetailResponseDTO) {
                ((FundDetailResponseDTO) result).setScore(scoreValue);
            } else if (result instanceof ForexDetailResponseDTO) {
                ((ForexDetailResponseDTO) result).setScore(scoreValue);
            } else if (result instanceof PensionDetailResponseDTO) {
                ((PensionDetailResponseDTO) result).setScore(scoreValue);
            }

        return result;
    }

    @Override
    @Async
    @Transactional
    public void incrementProductViewCountAsync(String productId) {
        handleProductByConsumer(
            productId,
            productDetailMapper::incrementDepositViewCount,
            productDetailMapper::incrementSavingsViewCount,
            productDetailMapper::incrementFundViewCount,
            productDetailMapper::incrementForexViewCount,
            productDetailMapper::incrementPensionViewCount);
    }

    @Transactional(readOnly=true)
    @Override
    public String checkRecommendProductStatus(@AuthenticationPrincipal CustomUser user,
        String productId) {

        return productDetailMapper.selectProductStatus(user.getUser().getId(), productId);
    }
    @Transactional
    @Override
    public void updateProductStatus(@AuthenticationPrincipal CustomUser user, String productId, String productStatus) {
        productDetailMapper.updateProductStatus(user.getUser().getId(), productId, productStatus);
    }
}
