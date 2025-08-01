package com.halggeol.backend.products.mapper;

import java.util.Date;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductFeedbackMapper {

    // 피드백 생성 메서드
    void createFeedback(@Param("pid") String productId,@Param("uid") String userId,@Param("reason") String feedbackReason,@Param("anlzDate") Date anlz_date);

}
