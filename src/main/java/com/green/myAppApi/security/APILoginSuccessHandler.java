package com.green.myAppApi.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.myAppApi.jwt.JWTUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	private JWTUtil jWTUtil;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		
		log.info("로그인성공하였숩니다!$$$$$$$$$$$$$$$$$$$$$$$$$");
		MemberDTO member=(MemberDTO) authentication.getPrincipal();
		Map<String, Object>claims=member.getClaims();
		
		String accessToken=jWTUtil.generateToken(claims, 10);
		
		System.out.println("accessToken"+accessToken);
		
		claims.put("accessToken", jWTUtil.generateToken(claims, 10));
		claims.put("refreshToken", jWTUtil.generateToken(claims, 60*24));
		
		ObjectMapper mapper=new ObjectMapper();
		String jsonStr=mapper.writeValueAsString(claims);
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw=response.getWriter();
		pw.println(jsonStr);
		pw.close();
		

	}

}
