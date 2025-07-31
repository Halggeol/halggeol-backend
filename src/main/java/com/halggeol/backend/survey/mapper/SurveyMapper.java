package com.halggeol.backend.survey.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SurveyMapper {
    int updateKnowledgeByEmail(
        @Param("email") String email,
        @Param("userKlg") int userKlg
    );

    int updateTendencyByEmail(
        @Param("email") String email,
        @Param("risk") int risk,
        @Param("yieldScore") double yieldScore,
        @Param("riskScore") double riskScore,
        @Param("costScore") double costScore,
        @Param("liquidityScore") double liquidityScore,
        @Param("complexityScore") double complexityScore
    );
}
