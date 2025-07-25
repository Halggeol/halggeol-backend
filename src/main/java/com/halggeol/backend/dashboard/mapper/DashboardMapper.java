package com.halggeol.backend.dashboard.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DashboardMapper {
    Double getAvgRegretScoreByUserId(String userId);
}
