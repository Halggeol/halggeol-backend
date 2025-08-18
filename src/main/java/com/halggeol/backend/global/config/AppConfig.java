package com.halggeol.backend.global.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableScheduling
@PropertySource({"classpath:/application.properties"})
@MapperScan({
    "com.halggeol.backend.**.mapper",
    // TODO: 도메인 고정 시 빠른 빌드를 위해 Scan 범위를 좁히기
//    "com.halggeol.backend.mapper",
//    "com.halggeol.backend.user.mapper",
//    "com.halggeol.backend.dashboard.mapper",
////    "com.halggeol.backend.products.mapper",
//    "com.halggeol.backend.products.**.mapper",
//    "com.halggeol.backend.recommend.mapper",
//    "com.halggeol.backend.scrap.mapper",
//    "com.halggeol.backend.security.mapper",
//    "com.halggeol.backend.survey.mapper",
////    "com.halggeol.backend.insight.mapper",
})
@ComponentScan(
    // TODO: 도메인 고정 시 빠른 빌드를 위해 Scan 범위를 좁히기
    basePackages = {"com.halggeol.backend", "com.halggeol.backend.user.service", "com.halggeol.backend.security", "com.halggeol.backend.products.deposit.controller","com.halggeol.backend.insight"},
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = RestController.class)
    }
)

@Slf4j
@EnableTransactionManagement
@EnableAsync
public class AppConfig {
    @Value("${jdbc.driver}") String driver;
    @Value("${jdbc.url}") String url;
    @Value("${jdbc.username}") String username;
    @Value("${jdbc.password}") String password;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setConfigLocation(
            applicationContext.getResource("classpath:/mybatis/mybatis-config.xml"));
        sqlSessionFactory.setDataSource(dataSource());
        return sqlSessionFactory.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(){
        DataSourceTransactionManager manager = new DataSourceTransactionManager(dataSource());
        return manager;
    }

}
