package com.halggeol.backend.products.service;

import com.halggeol.backend.products.mapper.ProductFeedbackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFeedbackServiceImpl implements ProductFeedbackService{


    private final ProductFeedbackMapper mapper;


}
