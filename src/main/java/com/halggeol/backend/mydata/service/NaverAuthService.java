package com.halggeol.backend.mydata.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.halggeol.backend.mydata.dto.NaverUserInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class NaverAuthService {

    @Value("${naver.auth.client-id}")
    private String clientId;

    @Value("${naver.auth.client-secret}")
    private String clientSecret;

    @Value("${naver.auth.redirect-uri}")
    private String redirectUri;

    @Value("${naver.auth.certificate-redirect-uri}")
    private String certificateRedirectUri;

    @Value("${naver.auth.token-uri}")
    private String tokenUri;

    @Value("${naver.auth.authorize-uri}")
    private String authorizeUri;

    @Value("${naver.auth.user-info-uri}")
    private String userInfoUri;

    private static final String STATE = "halggeol";

    private final RestTemplate restTemplate;

    @Autowired
    public NaverAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAccessToken(String code, String state, String redirectUri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);
        body.add("state", state);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(
            tokenUri,
            HttpMethod.POST,
            requestEntity,
            JsonNode.class
        );

        JsonNode responseBody = responseEntity.getBody();
        if (responseBody != null && responseBody.has("access_token")) {
            return responseBody.get("access_token").asText();
        }

        return null;
    }

    public String getNaverAuthorizeUrl() {
        return String.format("%s?response_type=code&client_id=%s&redirect_uri=%s&state=%s",
            authorizeUri, clientId, redirectUri, STATE);
    }

    public String getNaverCertificateAuthorizeUrl() {
        return String.format("%s?response_type=code&client_id=%s&redirect_uri=%s&state=%s&scope=auth_age,auth_birthdate,auth_gender,auth_name,auth_mobile",
            authorizeUri, clientId, certificateRedirectUri, STATE);
    }

    public NaverUserInfo getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<NaverUserInfo> responseEntity = restTemplate.exchange(
            userInfoUri,
            HttpMethod.GET,
            requestEntity,
            NaverUserInfo.class
        );

        return responseEntity.getBody();
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getCertificateRedirectUri() {
        return certificateRedirectUri;
    }
}
