package com.halggeol.backend.logs.config;


import com.halggeol.backend.security.domain.User;
import com.halggeol.backend.domain.UserActionLog;
import com.halggeol.backend.logs.processor.UserActionLogProcessor;
import com.halggeol.backend.logs.repository.UserActionLogRepository;
import com.halggeol.backend.recommend.mapper.RecommendMapper;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.binding.BindingException;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class UserActionBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final UserActionLogRepository repo;
    private final RecommendMapper recommendMapper;

    @Bean
    Job userActionLogJob(MongoItemReader<UserActionLog> mongoActionLogReader,
        SkipListener<UserActionLog, User> batchSkipListener,
        StepExecutionListener stepSummaryListener,
        org.springframework.batch.core.JobExecutionListener jobSummaryListener // ✅ 메서드 파라미터로 주입
    ) {
        return jobBuilderFactory.get("userActionLogJob")
            .start(userActionLogStep(mongoActionLogReader, batchSkipListener, stepSummaryListener))
            .listener(jobSummaryListener) // ✅ 이미 주입된 빈 사용 (자기 메서드 직접 호출 X)
            .build();
    }

    @Bean
    public Step userActionLogStep(MongoItemReader<UserActionLog> mongoActionLogReader,
        SkipListener<UserActionLog, User> batchSkipListener,
        StepExecutionListener stepSummaryListener) {
        return stepBuilderFactory.get("userActionSummaryStep")
            .<UserActionLog, User> chunk(100)
            .reader(mongoActionLogReader)
            .processor(mysqlLogProcessor())
            .writer(mysqlLogWriter())
            .faultTolerant()
            //일시적 오류에 대해 재시도 설정
            .retry(DeadlockLoserDataAccessException.class)
            .retry(CannotAcquireLockException.class)
            .retry(TransientDataAccessResourceException.class)
            .retryLimit(3)
            // 특정 예외에 대해 건너뛰기 설정
            .skip(BindingException.class)
            .skip(DuplicateKeyException.class)
            .skipLimit(100)

            .listener(batchSkipListener)
            .listener(stepSummaryListener)
            .build();
    }

    @Bean
    @StepScope
    public MongoItemReader<UserActionLog> mongoActionLogReader(MongoTemplate mongoTemplate) {
        ZoneId KST = ZoneId.of("Asia/Seoul");
        LocalDateTime today = LocalDateTime.now(KST);
        Date from = Date.from(today.minusMinutes(15).atZone(KST).toInstant());
        Date to = Date.from(today.atZone(KST).toInstant());

        return new MongoItemReaderBuilder<UserActionLog>()
            .name("mongoLogReader")
            .template(mongoTemplate)
            .jsonQuery("{ 'timestamp': { $gte: ?0, $lt: ?1 } }")
            .parameterValues(List.of(from, to))
            .targetType(UserActionLog.class)
            .sorts(Map.of("_id", Sort.Direction.ASC))
            .pageSize(100)
            .build();

//        return new ItemReader<>() {
//            @Override
//            public UserActionLog read() {
//                // MongoDB에서 UserActionLog를 읽어오는 로직을 구현합니다.
//                // 예시로 null을 반환하여 반복을 종료합니다.
//                List<UserActionLog> results = aggregateUserActions();
//                if (results.isEmpty()) {
//                    return null; // 더 이상 읽을 데이터가 없으면 null 반환
//                }
//                return results.remove(0); // 실제 구현에서는 MongoDB에서 데이터를 읽어와야 합니다.
//            }
//
//            private List<UserActionLog> aggregateUserActions() {
//                // MongoDB에서 UserActionLog를 하루 단위로 집계하는 로직을 구현합니다.
//                Date today = new Date();
//                // 리스트의 내용을 가져오는 로직을 작성합니다.
//                // 현재 날짜 이전 하루의 UserActionLog를 가져오는 예시입니다.
//                return repo.findAllByTimestampAfter((new Date(today.getTime() - 24 * 60 * 60 * 1000)));
//            }
//        };
    }

    @Bean
    public ItemProcessor<UserActionLog, User> mysqlLogProcessor() {
        return new UserActionLogProcessor(recommendMapper);
    }

    @Bean
    public ItemWriter<User> mysqlLogWriter() {
        return new JdbcBatchItemWriterBuilder<User>()
            .dataSource(dataSource)
            .sql("""
                UPDATE users SET
                    complexity_score = :complexityScore,
                    liquidity_score = :liquidityScore,
                    cost_score = :costScore,
                    yield_score = :yieldScore,
                    risk_score = :riskScore
                WHERE id = :id
                """)
            .beanMapped()
            .assertUpdates(false) // 업데이트가 없더라도 예외 발생 방지
            .build();
    }

    @Bean
    public org.springframework.batch.core.StepExecutionListener stepSummaryListener() {
        return new org.springframework.batch.core.listener.StepExecutionListenerSupport() {
            @Override
            public ExitStatus afterStep(org.springframework.batch.core.StepExecution se) {
                int read = se.getReadCount();
                int write = se.getWriteCount();
                int skip = se.getSkipCount();
                int fail = se.getFailureExceptions() == null ? 0 : se.getFailureExceptions().size();
                System.out.printf("[STEP] %s read=%d write=%d skip=%d fail=%d%n",
                    se.getStepName(), read, write, skip, fail);
                return se.getExitStatus();
            }
        };
    }

    @Bean
    public org.springframework.batch.core.JobExecutionListener jobSummaryListener(
        JavaMailSender mailSender,
        @Value("${mail.to}") String toList,
        @Value("${mail.from:}") String fromAddr // 없으면 생략
    ) {
        return new org.springframework.batch.core.listener.JobExecutionListenerSupport() {
            @Override
            public void afterJob(org.springframework.batch.core.JobExecution je) {
                var status = je.getStatus();
                var steps = je.getStepExecutions();

                int totalRead  = steps.stream().mapToInt(org.springframework.batch.core.StepExecution::getReadCount).sum();
                int totalWrite = steps.stream().mapToInt(org.springframework.batch.core.StepExecution::getWriteCount).sum();
                int totalSkip  = steps.stream().mapToInt(org.springframework.batch.core.StepExecution::getSkipCount).sum();

                String jobName = je.getJobInstance().getJobName();
                String subject = String.format("[Batch] %s: %s (read=%d, write=%d, skip=%d)",
                    jobName, status, totalRead, totalWrite, totalSkip);

                StringBuilder body = new StringBuilder();
                body.append("Job: ").append(jobName).append('\n')
                    .append("Status: ").append(status).append('\n')
                    .append("Read: ").append(totalRead).append('\n')
                    .append("Write: ").append(totalWrite).append('\n')
                    .append("Skip: ").append(totalSkip).append('\n')
                    .append("Start: ").append(je.getStartTime()).append('\n')
                    .append("End: ").append(je.getEndTime()).append('\n')
                    .append("\nParameters:\n");

                je.getJobParameters().getParameters().forEach((k, v) ->
                    body.append(" - ").append(k).append(" = ").append(v.getValue()).append('\n')
                );

                if (!je.getFailureExceptions().isEmpty()) {
                    body.append("\nFailures:\n");
                    je.getFailureExceptions().forEach(ex ->
                        body.append(" - ").append(ex.getClass().getSimpleName())
                            .append(": ").append(ex.getMessage()).append('\n')
                    );
                }

                // 알림 보낼 조건(예: 실패 또는 skip 발생 시만)
                boolean shouldNotify = !status.isUnsuccessful() ? (totalSkip > 0) : true;

                try {
                    if (shouldNotify) {
                        SimpleMailMessage msg = new SimpleMailMessage();
                        if (fromAddr != null && !fromAddr.isBlank()) msg.setFrom(fromAddr);
                        msg.setTo(parseRecipients(toList));
                        msg.setSubject(subject);
                        msg.setText(body.toString());
                        mailSender.send(msg);
                    }
                } catch (Exception e) {
                    // 메일 실패로 Job 자체가 실패하지 않도록 안전하게 처리
                    System.err.println("[JOB][MAIL] send failed: " + e.getMessage());
                }

                // 콘솔 로그 요약
                System.out.printf("[JOB] %s status=%s read=%d write=%d skip=%d%n",
                    jobName, status, totalRead, totalWrite, totalSkip);
            }

            private String[] parseRecipients(String csv) {
                return java.util.Arrays.stream(csv.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toArray(String[]::new);
            }
        };
    }
}
