package com.halggeol.backend.security.mail.service;

import com.halggeol.backend.security.mail.domain.MailType;
import com.halggeol.backend.security.mail.dto.MailDTO;
import com.mashape.unirest.http.HttpResponse; // unirest v1.4.9
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
        settingByMailType(mail.getMailType());

        try {
            HttpResponse<JsonNode> response = Unirest.post(url)
                .basicAuth("api", apiKey)
                .queryString("from", senderName + " <" + senderEmail + ">")
                .queryString("to", mail.getEmail())
                .queryString("subject", subject)
                .queryString("text", body + mail.getToken())
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

    public void settingByMailType(MailType mailType) {
        switch (mailType) {
            case SIGNUP:
                subject = "Signup to Halggeol";
                body = "/api/signup?token=";
                break;

            case PASSWORD_RESET:
                subject = "Password Reset for Halggeol";
                body = "/api/password/reset?token=";
                break;

            default:
                throw new IllegalArgumentException("Unknown mail type: " + mailType);
        }
    }
}
