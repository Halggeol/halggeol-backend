package com.halggeol.backend.products.pension.service;

import com.halggeol.backend.domain.Pension;
import com.halggeol.backend.products.pension.mapper.PensionMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PensionServiceImpl implements PensionService {

    private final PensionMapper pensionMapper;

    @Override
    public List<Pension> getAll() {
        return pensionMapper.getAll();
    }
}
