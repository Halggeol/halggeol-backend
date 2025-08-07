package com.halggeol.backend.security.mail.service;

import com.halggeol.backend.security.mail.domain.MailType;
import com.halggeol.backend.security.mail.dto.MailDTO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    @Value("${frontend.url}")
    private String frontendUrl;

    @Value("${mailgun.apiKey}")
    private String apiKey;

    @Value("${mailgun.url}")
    private String url;

    @Value("${mailgun.senderName}")
    private String senderName;

    @Value("${mailgun.senderEmail}")
    private String senderEmail;

    private String subject, body;

    public void sendMail(@Valid MailDTO mail) {
        settingByMailType(mail.getMailType(), mail.getToken());

        try {
            HttpResponse<JsonNode> response = Unirest.post(url)
                .basicAuth("api", apiKey)
                .field("from", senderName + " <" + senderEmail + ">")
                .field("to", mail.getEmail())
                .field("subject", subject)
                .field("html", body)
                .asJson();

            int statusCode = response.getStatus();

            if (statusCode >= 300) {
                HttpStatus httpStatus = HttpStatus.resolve(statusCode);
                if (httpStatus == null) {
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                }
                throw new ResponseStatusException(httpStatus, response.getBody().toString());
            }
        } catch (UnirestException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void settingByMailType(MailType mailType, String token) {
        switch (mailType) {
            case SIGNUP:
                subject = "Signup to Halggeol";
                body = "<p>아래 링크를 클릭하여 회원가입을 완료해주세요:</p>\n"
                    + "<a href=\"" + frontendUrl + "/signup?token=" + token
                    + "\">회원가입 링크</a>"
                    + "\n(" + frontendUrl + "/signup?token="
                    + token + ")";
                break;

            case PASSWORD_RESET:
                subject = "Password Reset for Halggeol";
                body = "<p>아래 링크를 클릭하여 비밀번호 재설정을 완료해주세요:</p>\n"
                    + "<a href=\"" + frontendUrl + "/find/password/reset?token=" + token
                    + "\">비밀번호 재설정 링크</a>"
                    + "\n(" + frontendUrl + "/find/password/reset?token="
                    + token + ")";
                break;

            default:
                throw new IllegalArgumentException("Unknown mail type: " + mailType);
        }
    }
}
