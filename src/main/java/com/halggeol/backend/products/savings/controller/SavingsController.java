package com.halggeol.backend.products.savings.controller;

import com.halggeol.backend.domain.Savings;
import com.halggeol.backend.products.savings.service.SavingsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/savings")
@RequiredArgsConstructor
public class SavingsController {

    private final SavingsService savingsService;

    @GetMapping
    public List<Savings> getAllSavings() {
        return savingsService.getAll();
    }

}
