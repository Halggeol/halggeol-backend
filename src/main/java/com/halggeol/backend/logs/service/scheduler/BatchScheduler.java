package com.halggeol.backend.logs.service.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job userActionLogJob;


    @Scheduled(cron = "30 0 0 * * *") // 매일 자정에 실행
    public void runJob() throws Exception {
        System.out.println("Batch job started at: " + new java.util.Date());
        // Job 실행
        JobParameters params = new JobParametersBuilder()
            .addLong("timestamp", System.currentTimeMillis())
            .toJobParameters();
        jobLauncher.run(userActionLogJob, params);
    }
}
