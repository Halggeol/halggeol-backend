package com.halggeol.backend.products.service;

import static com.halggeol.backend.common.ProductPrefixHandler.handleProductByBiFunction;
import static com.halggeol.backend.common.ProductPrefixHandler.handleProductByConsumer;

import com.halggeol.backend.logs.service.LogService;
import com.halggeol.backend.products.dto.DepositDetailResponseDTO;
import com.halggeol.backend.products.dto.ForexDetailResponseDTO;
import com.halggeol.backend.products.dto.FundDetailResponseDTO;
import com.halggeol.backend.products.dto.PensionDetailResponseDTO;
import com.halggeol.backend.products.dto.SavingsDetailResponseDTO;
import com.halggeol.backend.products.dto.UserSpecificDataResponseDTO;
import com.halggeol.backend.products.mapper.ProductDetailMapper;
import com.halggeol.backend.products.unified.dto.UnifiedProductRegretRankingResponseDTO;
import com.halggeol.backend.products.unified.service.UnifiedProductService;
import com.halggeol.backend.recommend.service.RecommendService;
import com.halggeol.backend.domain.CustomUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailMapper productDetailMapper;
    private final RecommendService recommendService;
    private final LogService logService;
    private final UnifiedProductService unifiedProductService;
    private final CacheManager cacheManager;

    @Override
    @Transactional
    public Object getProductDetailById(String productId, CustomUser user) {

        String userId = (user != null) ? String.valueOf(user.getUser().getId()) : null;


        incrementProductViewCountAsync(productId);

        if (userId != null) {
            logService.buildLog("view", productId, Integer.valueOf(userId));
        }

        Object result = handleProductByBiFunction(
            productId,
            userId,
            this::getDepositDetail,
            this::getSavingsDetail,
            this::getFundDetail,
            this::getForexDetail,
            this::getPensionDetail
        );

        if (userId != null && result != null) {
            Double matchScore = recommendService.getProductMatchScore(productId, userId);
            Integer scoreValue = null;
            if (matchScore != null) {
                scoreValue = (int) Math.round(matchScore * 100);
            }

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
        }
        return result;
    }


    private DepositDetailResponseDTO getDepositDetail(String productId, String userId) {
        Cache cache = cacheManager.getCache("deposit-detail");
        Cache.ValueWrapper valueWrapper = cache.get(productId);
        DepositDetailResponseDTO baseDto;

        if (valueWrapper != null) { // Cache Hit
            baseDto = (DepositDetailResponseDTO) valueWrapper.get();
        } else { // Cache Miss
            baseDto = productDetailMapper.selectBaseDepositDetailById(productId);
            if (baseDto != null) {
                cache.put(productId, baseDto);
            }
        }

        if (baseDto == null) return null;

        if (userId != null) {
            UserSpecificDataResponseDTO userDto = productDetailMapper.selectUserSpecificDataForDeposit(userId, productId);
            if (userDto != null) {
                baseDto.setAdvantage(userDto.getAdvantage());
                baseDto.setDisadvantage(userDto.getDisadvantage());
                baseDto.setIsScraped(userDto.isScraped());
            }
        }
        return baseDto;
    }

    private SavingsDetailResponseDTO getSavingsDetail(String productId, String userId) {
        Cache cache = cacheManager.getCache("savings-detail");
        Cache.ValueWrapper valueWrapper = cache.get(productId);
        SavingsDetailResponseDTO baseDto;

        if (valueWrapper != null) { // Cache Hit
            baseDto = (SavingsDetailResponseDTO) valueWrapper.get();
        } else { // Cache Miss
            baseDto = productDetailMapper.selectBaseSavingsDetailById(productId);
            if (baseDto != null) {
                cache.put(productId, baseDto);
            }
        }

        if (baseDto == null) return null;

        if (userId != null) {
            UserSpecificDataResponseDTO userDto = productDetailMapper.selectUserSpecificDataForSavings(userId, productId);
            if (userDto != null) {
                baseDto.setAdvantage(userDto.getAdvantage());
                baseDto.setDisadvantage(userDto.getDisadvantage());
                baseDto.setIsScraped(userDto.isScraped());
            }
        }
        return baseDto;
    }

    private FundDetailResponseDTO getFundDetail(String productId, String userId) {
        Cache cache = cacheManager.getCache("fund-detail");
        Cache.ValueWrapper valueWrapper = cache.get(productId);
        FundDetailResponseDTO baseDto;

        if (valueWrapper != null) { // Cache Hit
            baseDto = (FundDetailResponseDTO) valueWrapper.get();
        } else { // Cache Miss
            baseDto = productDetailMapper.selectBaseFundDetailById(productId);
            if (baseDto != null) {
                cache.put(productId, baseDto);
            }
        }

        if (baseDto == null) return null;

        if (userId != null) {
            UserSpecificDataResponseDTO userDto = productDetailMapper.selectUserSpecificDataForFund(userId, productId);
            if (userDto != null) {
                baseDto.setAdvantage(userDto.getAdvantage());
                baseDto.setDisadvantage(userDto.getDisadvantage());
                baseDto.setIsScraped(userDto.isScraped());
            }
        }
        return baseDto;
    }

    private ForexDetailResponseDTO getForexDetail(String productId, String userId) {
        Cache cache = cacheManager.getCache("forex-detail");
        Cache.ValueWrapper valueWrapper = cache.get(productId);
        ForexDetailResponseDTO baseDto;

        if (valueWrapper != null) { // Cache Hit
            baseDto = (ForexDetailResponseDTO) valueWrapper.get();
        } else { // Cache Miss
            baseDto = productDetailMapper.selectBaseForexDetailById(productId);
            if (baseDto != null) {
                cache.put(productId, baseDto);
            }
        }

        if (baseDto == null) return null;

        if (userId != null) {
            UserSpecificDataResponseDTO userDto = productDetailMapper.selectUserSpecificDataForForex(userId, productId);
            if (userDto != null) {
                baseDto.setAdvantage(userDto.getAdvantage());
                baseDto.setDisadvantage(userDto.getDisadvantage());
                baseDto.setIsScraped(userDto.isScraped());
            }
        }
        return baseDto;
    }

    private PensionDetailResponseDTO getPensionDetail(String productId, String userId) {
        Cache cache = cacheManager.getCache("pension-detail");
        Cache.ValueWrapper valueWrapper = cache.get(productId);
        PensionDetailResponseDTO baseDto;

        if (valueWrapper != null) { // Cache Hit
            baseDto = (PensionDetailResponseDTO) valueWrapper.get();
        } else { // Cache Miss
            baseDto = productDetailMapper.selectBasePensionDetailById(productId);
            if (baseDto != null) {
                cache.put(productId, baseDto);
            }
        }

        if (baseDto == null) return null;

        if (userId != null) {
            UserSpecificDataResponseDTO userDto = productDetailMapper.selectUserSpecificDataForPension(userId, productId);
            if (userDto != null) {
                baseDto.setAdvantage(userDto.getAdvantage());
                baseDto.setDisadvantage(userDto.getDisadvantage());
                baseDto.setIsScraped(userDto.isScraped());
            }
        }
        return baseDto;
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

    @Scheduled(cron = "0 0 * * * *")
    @Override
    public void saveCacheRegretRankingProducts() {
        List<UnifiedProductRegretRankingResponseDTO> ranking = unifiedProductService.getRegretRankingProducts();

        if (ranking == null || ranking.isEmpty()) {
            return;
        }

        for (UnifiedProductRegretRankingResponseDTO rankedProduct : ranking) {
            String productId = rankedProduct.getProductId();
            if (productId == null || productId.isEmpty()) {
                continue;
            }

            handleProductByConsumer(
                productId,
                id -> {
                    Object dto = productDetailMapper.selectBaseDepositDetailById(id);
                    cacheManager.getCache("deposit-detail").put(id, dto);
                },
                id -> {
                    Object dto = productDetailMapper.selectBaseSavingsDetailById(id);
                    cacheManager.getCache("savings-detail").put(id, dto);
                },
                id -> {
                    Object dto = productDetailMapper.selectBaseFundDetailById(id);
                    cacheManager.getCache("fund-detail").put(id, dto);
                },
                id -> {
                    Object dto = productDetailMapper.selectBaseForexDetailById(id);
                    cacheManager.getCache("forex-detail").put(id, dto);
                },
                id -> {
                    Object dto = productDetailMapper.selectBasePensionDetailById(id);
                    cacheManager.getCache("pension-detail").put(id, dto);
                }
            );

        }
    }

    // TODO: 추후 비동기 처리 스레드 에러 확인 및 구현
//    @Async
//    @Transactional
//    @Override
//    public void cacheUserRecommendedProducts(String userId, List<RecommendResponseDTO> recommendedProducts) {
//        if (userId == null || recommendedProducts == null || recommendedProducts.isEmpty()) {
//            return;
//        }
//
//        log.info("🚀 [User Cache Warmer] 사용자 ID {}의 추천 상품 {}개 캐싱을 시작합니다.", userId, recommendedProducts.size());
//
//        for (RecommendResponseDTO product : recommendedProducts) {
//            String productId = product.getProductId();
//
//            Object finalDto = handleProductByBiFunction(
//                productId,
//                userId,
//                this::getDepositDetail,
//                this::getSavingsDetail,
//                this::getFundDetail,
//                this::getForexDetail,
//                this::getPensionDetail
//            );
//
//            if (finalDto != null) {
//                String cacheKey = userId + "::" + productId;
//                cacheManager.getCache("user-product-detail").put(cacheKey, finalDto);
//            }
//        }
//    }
}
