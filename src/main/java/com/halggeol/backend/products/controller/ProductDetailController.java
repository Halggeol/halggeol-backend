package com.halggeol.backend.products.controller;

import com.halggeol.backend.domain.Deposit;
import com.halggeol.backend.products.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/detail")
@RequiredArgsConstructor
public class ProductDetailController {
    private final ProductDetailService productDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<Deposit> getDepositDetailById(@PathVariable String id) {

//        DepositResponseDTO response = productDetailService.getDepositById(id);
        Deposit response = productDetailService.getDepositDetailById(id);

        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
        return new ResponseEntity<>(response, HttpStatus.OK); // 200 OK
    }
}
