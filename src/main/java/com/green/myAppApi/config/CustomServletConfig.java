package com.green.myAppApi.config;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer{
	/*
	@Override
	public void addFormatters(FormatterRegistry registry) {
	    // LocalDateTime을 특정 형식으로 처리하기 위한 포맷터 등록
	    DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
	    
	    // 원하는 형식으로 포맷터를 등록
	    registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	    
	    // 포맷터 등록
	    registrar.registerFormatters(registry);
	}
	*/

	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    // 모든 경로에 대해 CORS 설정을 추가한다.
	    registry.addMapping("/**") // 모든 URL 패턴에 대해 CORS 허용
	            .allowedOrigins("*") // 모든 도메인에 대해 접근을 허용한다. (모든 출처 허용)
	            .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드들을 지정
	            .maxAge(300) // 캐시할 최대 시간(초 단위), 즉 preflight 요청의 응답을 300초 동안 캐시한다.
	            .allowedHeaders("Authorization", "Cache-Control", "Content-Type"); // 요청에서 허용할 헤더들을 지정
	}

	
	
	
}
