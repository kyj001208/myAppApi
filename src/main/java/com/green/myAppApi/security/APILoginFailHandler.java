package com.green.myAppApi.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class APILoginFailHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		log.info("----------------------------로그인 실패-----------------------");
		
		log.info("::"+exception);
		
		ObjectMapper mapper=new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(Map.of("error", "아이디 또는 비번이 틀렸습니다"));

		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw=response.getWriter();
		pw.println();
		pw.close();

	}

}
