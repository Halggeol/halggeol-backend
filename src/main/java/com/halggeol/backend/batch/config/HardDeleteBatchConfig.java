package com.halggeol.backend.batch.config;

import com.halggeol.backend.domain.User;
import java.time.LocalDateTime;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class HardDeleteBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final SqlSessionFactory sqlSessionFactory;

    @Bean
    public Job hardDeleteJob() {
        return jobBuilderFactory.get("hardDeleteJob")
            .start(hardDeleteStep())
            .build();
    }

    @Bean
    public Step hardDeleteStep() {
        return stepBuilderFactory.get("hardDeleteStep")
            .<User, User>chunk(100)
            .reader(userItemReader())
            .processor(userItemProcessor())
            .writer(userItemWriter())
            .build();
    }

    @Bean
    public MyBatisPagingItemReader<User> userItemReader() {
        MyBatisPagingItemReader<User> reader = new MyBatisPagingItemReader<>();
        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setQueryId("com.halggeol.backend.user.mapper.UserMapper.findUsersDeletedBefore");
        reader.setParameterValues(
            Collections.singletonMap("deletedDate", LocalDateTime.now().minusDays(30)));
        reader.setPageSize(100);
        return reader;
    }

    @Bean
    public ItemProcessor<User, User> userItemProcessor() {
        return user -> {
            log.info(
                "Processing user for hard delete: id={}, username={}",
                user.getId(),
                user.getName()
            );
            return user;
        };
    }

    @Bean
    public MyBatisBatchItemWriter<User> userItemWriter() {
        MyBatisBatchItemWriter<User> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory);
        writer.setStatementId("com.halggeol.backend.user.mapper.UserMapper.deleteUserById");
        return writer;
    }
}
