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
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

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
    public DataSourceInitializer dataSourceInitializer() {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

        populator.addScript(new ClassPathResource("org/springframework/batch/core/schema-mysql.sql"));
        populator.setContinueOnError(true);
        // 데이터베이스 초기화 로직을 추가할 수 있습니다.
        // 예: 스키마 생성, 초기 데이터 삽입 등
        dataSourceInitializer.setDatabasePopulator(populator);
        return dataSourceInitializer;
    }

    @Bean Job userActionLogJob(MongoItemReader<UserActionLog> mongoActionLogReader) {
        return jobBuilderFactory.get("userActionLogJob")
            .start(userActionLogStep(mongoActionLogReader))
            .build();
    }

    @Bean
    public Step userActionLogStep(MongoItemReader<UserActionLog> mongoActionLogReader) {
        return stepBuilderFactory.get("userActionSummaryStep")
            .<UserActionLog, User> chunk(100)
            .reader(mongoActionLogReader)
            .processor(mysqlLogProcessor())
            .writer(mysqlLogWriter())
            .build();
    }

    @Bean
    @StepScope
    public MongoItemReader<UserActionLog> mongoActionLogReader(MongoTemplate mongoTemplate) {
        ZoneId KST = ZoneId.of("Asia/Seoul");
        LocalDateTime today = LocalDateTime.now(KST);
        Date from = Date.from(today.minusDays(1).atZone(KST).toInstant());
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
            .build();
    }
}
