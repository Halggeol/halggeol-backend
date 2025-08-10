package com.halggeol.backend.mydata.controller;

import com.halggeol.backend.mydata.dto.NaverUserInfo;
import com.halggeol.backend.mydata.service.NaverAuthService;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/naver")
@RequiredArgsConstructor
@Slf4j
public class NaverController {

    private final NaverAuthService naverAuthService;

    @GetMapping("/login")
    public RedirectView naverLogin() throws java.io.UnsupportedEncodingException {
        String url = naverAuthService.getNaverAuthorizeUrl();
        return new RedirectView(url);
    }

    @GetMapping("/callback")
    public RedirectView naverCallback(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) {
        String accessToken = naverAuthService.getAccessToken(code, state, naverAuthService.getRedirectUri());

        if (accessToken != null) {
            session.setAttribute("naverAccessToken", accessToken);
            // 로그인 성공 후 바로 네이버 인증서 발급/확인 흐름으로 리다이렉트
            return new RedirectView(naverAuthService.getNaverCertificateAuthorizeUrl());
        } else {
            return new RedirectView("/auth/naver/error");

        }
    }

    @GetMapping("/certificate")
    public RedirectView naverCertificate() {
        String url = naverAuthService.getNaverCertificateAuthorizeUrl();
        return new RedirectView(url);
    }

    @GetMapping("/certificate/callback")
    public ResponseEntity<NaverUserInfo> naverCertificateCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
        String accessToken = naverAuthService.getAccessToken(code, state, naverAuthService.getCertificateRedirectUri());
        if (accessToken != null) {
            NaverUserInfo userInfo = naverAuthService.getUserInfo(accessToken);
            return ResponseEntity.ok(userInfo);
        } else {
            return ResponseEntity.status(400).build();
        }
    }
}
