package com.halggeol.backend.global.config;


import com.halggeol.backend.security.config.SecurityConfig;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


@Configuration
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer implements
    WebMvcConfigurer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { AppConfig.class, SecurityConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { ServletConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    // POST body 문자 인코딩 필터 설정 - UTF-8 설정
   @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();

        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        return new Filter[] {characterEncodingFilter};
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // StringHttpMessageConverter 설정: String 반환 시 UTF-8 강제
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setSupportedMediaTypes(Arrays.asList(
            new MediaType("text", "plain", StandardCharsets.UTF_8), // 일반 텍스트 응답
            new MediaType("text", "html", StandardCharsets.UTF_8)   // HTML 텍스트 응답
        ));
        converters.add(stringConverter);
    }
}
