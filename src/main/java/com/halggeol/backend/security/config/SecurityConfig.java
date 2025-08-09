package com.halggeol.backend.security.config;

import com.halggeol.backend.security.filter.JwtAuthErrorFilter;
import com.halggeol.backend.security.filter.JwtAuthFilter;
import com.halggeol.backend.security.filter.JwtLoginAuthFilter;
import com.halggeol.backend.security.handler.CustomAccessDeniedHandler;
import com.halggeol.backend.security.handler.CustomAuthEntryPoint;
import com.halggeol.backend.security.util.JsonResponse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@Log4j2
@MapperScan(basePackages = {"com.halggeol.backend.security.mapper"})
@ComponentScan(basePackages = {"com.halggeol.backend.security"})
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAuthErrorFilter jwtAuthErrorFilter;
    private final CustomAuthEntryPoint authEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    // 순환 참조 문제 해결
    private JwtLoginAuthFilter jwtLoginAuthFilter;

    @Bean
    public Argon2PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public CharacterEncodingFilter encodingFilter() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return encodingFilter;
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    // 정적 리소스, Swagger, favicon 등 보안 처리 불필요한 경로 추가
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
            "/assets/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/api-docs"
        );
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 인코딩, 인증 관련 필터
        http.addFilterBefore(encodingFilter(), CsrfFilter.class)
            .addFilterBefore(jwtLoginAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthFilter, JwtLoginAuthFilter.class)
            .addFilterBefore(jwtAuthErrorFilter, JwtAuthFilter.class);

        // 접근 권한 설정
        http.authorizeRequests()
            .antMatchers(
                "/api/email/find",
                "/api/login",
                "/api/password/reset",
                "/api/password/reset/*",
                "/api/signup",
                "/api/signup/*",
                "/api/survey",
                "/api/main",
                "/api/products",
                "/api/products/basic",
                "/api/survey/knowledge/init",
                "/api/survey/tendency/init",
                "/api/insight",
                "/api/insight/*",
                "/api/insight/details",
                "/api/insight/details/*",
                "/api/main" // TODO: 비로그인 API 분리 필요,
            ).permitAll()                   // 비로그인 접근 허용
            .anyRequest().authenticated();  // 인증된 사용자만 접근 허용

        // 기본 인증 / CSRF 비활성화, 세션 stateless 설정
        http.httpBasic().disable()
            .csrf().disable()
            .cors().and()
            .formLogin().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 예외 핸들러 설정
        http.exceptionHandling()
            .authenticationEntryPoint(authEntryPoint)
            .accessDeniedHandler(accessDeniedHandler);

        http.logout()
            .logoutUrl("/api/logout")
            .logoutSuccessHandler((request, response, authentication) -> {
                JsonResponse.send(response, Map.of("message", "로그아웃 되었습니다."));
            });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }
}
