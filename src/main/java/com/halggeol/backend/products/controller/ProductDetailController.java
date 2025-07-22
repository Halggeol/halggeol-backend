package com.halggeol.backend.products.controller;

import com.halggeol.backend.domain.Deposit;
import com.halggeol.backend.products.dto.DepositDetailResponseDTO;
import com.halggeol.backend.products.dto.SavingsDetailResponseDTO;
import com.halggeol.backend.products.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Todo: 이후 jwt 되면 모든 userId 를 @AuthenticationPrincipal UserDetails userDetails로 변경
@RestController
@RequestMapping("/api/products/detail")
@RequiredArgsConstructor
public class ProductDetailController {
    private final ProductDetailService productDetailService;

    @GetMapping("/{depositId}")
    public ResponseEntity<DepositDetailResponseDTO> getDepositDetailById(
        @PathVariable String depositId,


        @RequestParam("userId") String userId) {


        DepositDetailResponseDTO response = productDetailService.getDepositDetailById(depositId, userId);

        return new ResponseEntity<>(response, HttpStatus.OK); // 200 OK
    }

    @GetMapping("/savings/{savingsId}")
    public ResponseEntity<SavingsDetailResponseDTO> getSavingsDetailById(
        @PathVariable String savingsId,
        @RequestParam("userId") String userId) {
        SavingsDetailResponseDTO response = productDetailService.getSavingsDetailById(savingsId, userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
