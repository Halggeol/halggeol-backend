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
import com.halggeol.backend.security.domain.CustomUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        // 상품 ID 접두사에 따라, 캐싱과 데이터 조합 로직이 포함된 내부 메소드를 호출합니다.
        Object result = handleProductByBiFunction(
            productId,
            userId,
            this::getDepositDetail,
            this::getSavingsDetail,
            this::getFundDetail,
            this::getForexDetail,
            this::getPensionDetail
        );

        if (userId != null) {
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
        DepositDetailResponseDTO baseDto = getBaseDepositDetail(productId);
        if (userId != null) {
            UserSpecificDataResponseDTO userDto = productDetailMapper.selectUserSpecificDataForDeposit(userId, productId);
            if(userDto != null) {
                baseDto.setAdvantage(userDto.getAdvantage());
                baseDto.setDisadvantage(userDto.getDisadvantage());
                baseDto.setIsScraped(userDto.isScraped());
            }
        }
        return baseDto;
    }

    private SavingsDetailResponseDTO getSavingsDetail(String productId, String userId) {
        SavingsDetailResponseDTO baseDto = getBaseSavingsDetail(productId);
        if (userId != null) {
            UserSpecificDataResponseDTO userDto = productDetailMapper.selectUserSpecificDataForSavings(userId, productId);
            if(userDto != null) {
                baseDto.setAdvantage(userDto.getAdvantage());
                baseDto.setDisadvantage(userDto.getDisadvantage());
                baseDto.setIsScraped(userDto.isScraped());
            }
        }
        return baseDto;
    }

    private FundDetailResponseDTO getFundDetail(String productId, String userId) {
        FundDetailResponseDTO baseDto = getBaseFundDetail(productId);
        if (userId != null) {
            UserSpecificDataResponseDTO userDto = productDetailMapper.selectUserSpecificDataForFund(userId, productId);
            if(userDto != null) {
                baseDto.setAdvantage(userDto.getAdvantage());
                baseDto.setDisadvantage(userDto.getDisadvantage());
                baseDto.setIsScraped(userDto.isScraped());
            }
        }
        return baseDto;
    }

    private ForexDetailResponseDTO getForexDetail(String productId, String userId) {
        ForexDetailResponseDTO baseDto = getBaseForexDetail(productId);
        if (userId != null) {
            UserSpecificDataResponseDTO userDto = productDetailMapper.selectUserSpecificDataForForex(userId, productId);
            if(userDto != null) {
                baseDto.setAdvantage(userDto.getAdvantage());
                baseDto.setDisadvantage(userDto.getDisadvantage());
                baseDto.setIsScraped(userDto.isScraped());
            }
        }
        return baseDto;
    }

    private PensionDetailResponseDTO getPensionDetail(String productId, String userId) {
        PensionDetailResponseDTO baseDto = getBasePensionDetail(productId);
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

    @Cacheable(value = "deposit-detail", key = "#productId")
    public DepositDetailResponseDTO getBaseDepositDetail(String productId) {
        return productDetailMapper.selectBaseDepositDetailById(productId);
    }

    @Cacheable(value = "savings-detail", key = "#productId")
    public SavingsDetailResponseDTO getBaseSavingsDetail(String productId) {
        return productDetailMapper.selectBaseSavingsDetailById(productId);
    }

    @Cacheable(value = "fund-detail", key = "#productId")
    public FundDetailResponseDTO getBaseFundDetail(String productId) {
        return productDetailMapper.selectBaseFundDetailById(productId);
    }

    @Cacheable(value = "forex-detail", key = "#productId")
    public ForexDetailResponseDTO getBaseForexDetail(String productId) {
        return productDetailMapper.selectBaseForexDetailById(productId);
    }

    @Cacheable(value = "pension-detail", key = "#productId")
    public PensionDetailResponseDTO getBasePensionDetail(String productId) {
        return productDetailMapper.selectBasePensionDetailById(productId);
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
}
