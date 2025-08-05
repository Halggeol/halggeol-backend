package com.halggeol.backend.recommend.service.calculater.strategy;

import com.halggeol.backend.recommend.service.calculater.model.Score;

public interface ScoreStrategy<T> {
    Score calculate(T dto);
}
