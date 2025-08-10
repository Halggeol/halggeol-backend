package com.halggeol.backend.mydata.controller;

import com.halggeol.backend.mydata.dto.MyDataAccountListResponseDTO;
import com.halggeol.backend.mydata.service.MyDataService;
import com.halggeol.backend.security.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mydata")
@RequiredArgsConstructor
@Slf4j
public class MyDataController {

    private final MyDataService myDataService;

    @GetMapping
    public ResponseEntity<MyDataAccountListResponseDTO> fetchMyData(@AuthenticationPrincipal CustomUser user) {
        MyDataAccountListResponseDTO response = myDataService.fetchAndSaveAccounts(user);
        return ResponseEntity.ok(response);
    }
}
