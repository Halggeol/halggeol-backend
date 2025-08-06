package com.halggeol.backend.insight.scheduler;

import com.halggeol.backend.insight.service.InsightService;
import com.halggeol.backend.insight.service.InsightServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class InsightScheduler {
    private final InsightService insightService;

    public InsightScheduler(InsightService insightService) {
        this.insightService = insightService;
    }

    @Scheduled(cron = "0 30 11 * * MON-FRI")  // 평일 11:30
    public void fetchDailyExchangeRate() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println("[스케줄러] 환율 조회·저장 시작: " + today);
        // 형변환 없이 인터페이스 메서드 호출!
        insightService.fetchAndSaveExchangeRates();
    }
}
