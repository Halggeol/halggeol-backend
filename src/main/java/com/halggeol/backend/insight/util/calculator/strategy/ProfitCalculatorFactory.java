package com.halggeol.backend.insight.util.calculator.strategy;

import com.halggeol.backend.insight.domain.ProductType;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ProfitCalculatorFactory {
    private final Map<ProductType, ProfitCalculatorStrategy> strategyMap;

    public ProfitCalculatorFactory(List<ProfitCalculatorStrategy> strategies) {
        this.strategyMap = strategies.stream().collect(Collectors.toUnmodifiableMap(ProfitCalculatorStrategy::getProductType,
            Function.identity()));
    }

    public ProfitCalculatorStrategy getStrategy(ProductType type) {
        return switch (type){
            case CONSERVATIVE -> strategyMap.get(ProductType.DEPOSIT);
            case AGGRESSIVE -> strategyMap.get(ProductType.FUND);
            default -> {
                ProfitCalculatorStrategy strategy = strategyMap.get(type);
                if (strategy == null) {
                    throw new IllegalArgumentException("지원하지 않는 상품 타입의 계산 전략입니다: " + type);
                }
                yield strategy;
            }
        };
    }
}