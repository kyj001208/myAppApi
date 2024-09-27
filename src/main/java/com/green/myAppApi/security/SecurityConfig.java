package com.green.myAppApi.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.green.myAppApi.jwt.JWTCheckFilter;
import com.green.myAppApi.jwt.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private  final JWTUtil jWTUtil;
	
	@Autowired
	private final APILoginSuccessHandler loginSuccessHandler;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean //보안 필터 체인을 빈으로 등록
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		

		log.info("---------------------security config---------------------------");

		//CORS 설정
		http.cors(httpSecurityCorsConfigurer -> {
			httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
		})

		//세선을 사용하지 않도록 설정
		.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

		//CSRF 보호 기능 비활성화
		.csrf(config -> config.disable())
		
		
		//폼 로그인 설정 
		.formLogin(config -> {
			config.loginPage("/api/member/login")
		
			.successHandler(loginSuccessHandler)
			.failureHandler(new APILoginFailHandler());
		});
		
		// JWT 검증 필터를 Username~ 앞에 추가
		http.addFilterBefore(new JWTCheckFilter(jWTUtil), 
				//인증이 성공할 경우 필터 내부에서 securitycontextholder에 인증된 사용자 정보를 저장
				UsernamePasswordAuthenticationFilter.class);// JWT체크
	
		//권한 부족시 처리할 핸들러 설정
		// http.exceptionHandling(config -> {
			// config.accessDeniedHandler(new CustomAccessDeniedHandler());
		// });
		
		return http.build();
	}

	@Bean //CORS 설정 소스를 빈으로 등록
	CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOriginPatterns(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

}
