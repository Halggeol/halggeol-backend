package com.halggeol.backend.scrap.mapper;

import com.halggeol.backend.scrap.domain.Scrap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScrapMapper {
    void insertUserScrap(Scrap scrap);
    void deleteUserScrap(@Param("userId") int userId, @Param("productId") String productId);
}
