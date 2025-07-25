package com.halggeol.backend.security.config;

import com.halggeol.backend.security.filter.JwtAuthErrorFilter;
import com.halggeol.backend.security.filter.JwtAuthFilter;
import com.halggeol.backend.security.filter.JwtLoginAuthFilter;
import com.halggeol.backend.security.handler.CustomAccessDeniedHandler;
import com.halggeol.backend.security.handler.CustomAuthEntryPoint;
import com.halggeol.backend.security.handler.LoginFailureHandler;
import com.halggeol.backend.security.handler.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
@Log4j2
@MapperScan(basePackages = {"com.halggeol.backend.security.mapper"})
@ComponentScan(basePackages = {"com.halggeol.backend.security"})
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAuthErrorFilter jwtAuthErrorFilter;
    private final CustomAuthEntryPoint customAuthEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    // 순환 참조 문제 해결
    private JwtLoginAuthFilter jwtLoginAuthFilter;

    @Bean
    public Argon2PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CharacterEncodingFilter encodingFilter() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return encodingFilter;
    }

    @Bean
    // 정적 리소스, Swagger, favicon 등 보안 처리 불필요한 경로 추가
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/api-docs"
        );
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.setAllowedOriginPatterns(List.of("*"));
//        config.setAllowedHeaders(List.of("*"));
//        config.setAllowedMethods(List.of("*"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 인코딩, 인증 관련 필터
        http.addFilterBefore(encodingFilter(), CorsFilter.class)
            .addFilterBefore(jwtLoginAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthFilter, JwtLoginAuthFilter.class)
            .addFilterBefore(jwtAuthErrorFilter, JwtAuthFilter.class);

        // 접근 권한 설정
        http.authorizeRequests()
            .antMatchers(
                "/api/email",
                "/api/login/",
                "/api/password/reset",
                "/api/signup",
                "/api/survey"
            ).permitAll()                   // 비로그인 접근 허용
            .anyRequest().authenticated();  // 인증된 사용자만 접근 허용

        // 기본 인증 / CSRF 비활성화, 세션 stateless 설정
        http.httpBasic().disable()
            .csrf().disable()
            .formLogin().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 예외 핸들러 설정
        http.exceptionHandling()
            .authenticationEntryPoint(customAuthEntryPoint)
            .accessDeniedHandler(customAccessDeniedHandler);

        http.logout()
            .logoutUrl("/api/logout")
            .invalidateHttpSession(true)
            .deleteCookies("remember-me", "JSESSIONID")
            .logoutSuccessUrl("/api/main");

        return http.build();
    }
}
