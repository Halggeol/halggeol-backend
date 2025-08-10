package com.halggeol.backend.mydata.service;

import com.halggeol.backend.domain.MyProduct;
import com.halggeol.backend.domain.Mydata;
import com.halggeol.backend.mydata.domain.MyDataAccount;
import com.halggeol.backend.mydata.dto.MyDataAccountListResponseDTO;
import com.halggeol.backend.mydata.mapper.MyDataMapper;
import com.halggeol.backend.security.domain.CustomUser;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyDataService {

    private final RestTemplate restTemplate;
    private final MyDataMapper myDataMapper;
    private String baseUrl;
    private String version;
    private String industry;
    private String orgCode;

    @Autowired
    public MyDataService(
        RestTemplate restTemplate,
        MyDataMapper myDataMapper,
        @Value("${mydata.api.base-url}") String baseUrl,
        @Value("${mydata.api.version}") String version,
        @Value("${mydata.api.industry}") String industry,
        @Value("${mydata.api.org-code}") String orgCode
    ) {
        this.restTemplate = restTemplate;
        this.myDataMapper = myDataMapper;
        this.baseUrl = baseUrl;
        this.version = version;
        this.industry = industry;
        this.orgCode = orgCode;
    }

//    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public MyDataAccountListResponseDTO fetchAndSaveAccounts(@AuthenticationPrincipal CustomUser user) {

        int userId = user.getUser().getId();

        try {
            String accessToken = "YOUR_ACCESS_TOKEN"; // 실제로는 userId 기반으로 토큰을 가져와야 함
            String tranId = UUID.randomUUID().toString().substring(0, 25);
            String apiType = "regular"; // on-demand 호출이므로 regular로 가정
            int limit = 500;

            MyDataAccountListResponseDTO response = callAccountListApi(accessToken, tranId, apiType, limit);

            Mydata mydata = Mydata.builder()
                .collectDate(LocalDateTime.now())
                .asset(response.getAccountCnt())
                .userId(userId)
                .build();
            myDataMapper.insertMydata(mydata);

            int mydataId = mydata.getId();

            for (MyDataAccount account : response.getAccountList()) {
                MyProduct myProduct = MyProduct.builder()
                    .mydataId(mydataId)
                    .amount(parseBalanceAmount(account.getBalanceAmount()))
                    .regDate(LocalDateTime.now())
                    .endDate(null)
                    .productId(mapAccountTypeToProductId(account.getAccountType()))
                    .build();
                myDataMapper.insertMyProduct(myProduct);
            }

            return response;

        } catch (Exception e) {
            throw new RuntimeException("데이터 처리 실패", e);
        }
    }

    private MyDataAccountListResponseDTO callAccountListApi(
        String accessToken,
        String tranId,
        String apiType,
        int limit
    ) {
        String url = String.format("%s/%s/%s/accounts", baseUrl, version, industry);

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("org_code", orgCode)
            .queryParam("limit", limit)
            .encode()
            .build()
            .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("x-api-tran-id", tranId);
        headers.set("x-api-type", apiType);

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        log.info("Calling MyData API: {}", uri);

        ResponseEntity<MyDataAccountListResponseDTO> response = restTemplate.exchange(
            uri,
            HttpMethod.GET,
            httpEntity,
            MyDataAccountListResponseDTO.class
        );

        return response.getBody();
    }

    private Integer parseBalanceAmount(String balanceAmount) {
        if (balanceAmount == null) return 0;
        try {
            return Integer.parseInt(balanceAmount);
        } catch (NumberFormatException e) {
            log.error("잔액 변환 오류: {}", balanceAmount, e);
            return 0;
        }
    }
    private String mapAccountTypeToProductId(String accountType) {
        return "";
    }
}
