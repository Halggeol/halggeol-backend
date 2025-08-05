package com.halggeol.backend.insight.service.strategy;

import com.halggeol.backend.insight.domain.ProductType;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class InsightDetailFactory {
    private final Map<ProductType, InsightDetailStrategy> strategyMap;

    public InsightDetailFactory(List<InsightDetailStrategy> strategies) {
        this.strategyMap = strategies.stream()
            .collect(Collectors.toUnmodifiableMap(InsightDetailStrategy::getProductType, Function.identity()));
    }

    public InsightDetailStrategy getStrategy(ProductType type) {
        InsightDetailStrategy strategy = strategyMap.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("지원하지 않는 상품 타입 " + type);
        }
        return strategy;
    }
}
