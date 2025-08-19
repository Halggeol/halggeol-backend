package com.halggeol.backend.logs.service.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job userActionLogJob;
    private final Job hardDeleteJob;


    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    public void runJob() throws Exception {
        System.out.println("Batch job started at: " + new java.util.Date());
        // Job 실행
        JobParameters params = new JobParametersBuilder()
            .addLong("timestamp", System.currentTimeMillis())
            .toJobParameters();
        jobLauncher.run(userActionLogJob, params);
        System.out.println("Batch job completed at: " + new java.util.Date());
    }

    @Scheduled(cron = "0 0 3 * * *") // 매일 새벽 3시에 실행
    public void runHardDeleteJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                .addLong("runDate", System.currentTimeMillis())
                .toJobParameters();

            jobLauncher.run(hardDeleteJob, params);
            log.info("Hard delete job started at: {}", new java.util.Date());
        } catch (Exception e) {
            log.error("Hard delete job failed: {}", e.getMessage());
        }
    }
}
