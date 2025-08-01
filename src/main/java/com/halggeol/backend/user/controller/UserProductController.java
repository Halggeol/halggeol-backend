package com.halggeol.backend.user.controller;

import com.halggeol.backend.security.domain.CustomUser;
import com.halggeol.backend.user.dto.UserProductResponseDTO;
import com.halggeol.backend.user.service.UserProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/me")
public class UserProductController {
     private final UserProductService userProductService;

     @GetMapping("/products")
     public ResponseEntity<List<UserProductResponseDTO>> getUserProductsByUserId(@AuthenticationPrincipal
         CustomUser user) {
         List<UserProductResponseDTO> products = userProductService.getUserProductsByUserId(user);
         return ResponseEntity.ok(products);
     }
}
