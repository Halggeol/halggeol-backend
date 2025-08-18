package com.halggeol.backend.global.config;


import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender mailSender(
        @Value("${mail.host:smtp.naver.com}") String host,
        @Value("${mail.port:587}") int port,
        @Value("${mail.username}") String username,   // ex) your_id@naver.com
        @Value("${mail.password}") String password,
        @Value("${mail.debug:false}") boolean debug,
        @Value("${mail.ssl:false}") boolean ssl      // true면 SSL(465) 강제
    ) {
        JavaMailSenderImpl s = new JavaMailSenderImpl();
        s.setHost(host);
        s.setPort(port);
        s.setUsername(username);
        s.setPassword(password);
        s.setDefaultEncoding("UTF-8");

        Properties p = s.getJavaMailProperties();
        p.put("mail.transport.protocol", "smtp");
        p.put("mail.smtp.auth", "true");
        p.put("mail.debug", String.valueOf(debug));

        // 465 또는 ssl=true → SSL(SMTPS)
        if (ssl || port == 465) {
            p.put("mail.smtp.ssl.enable", "true");
            p.put("mail.smtp.ssl.trust", host);
            p.put("mail.smtp.ssl.protocols", "TLSv1.2");
            // STARTTLS는 사용하지 않음
        } else {
            // 587 → STARTTLS
            p.put("mail.smtp.starttls.enable", "true");
        }
        return s;
    }
}