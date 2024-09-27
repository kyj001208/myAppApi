package com.green.myAppApi.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		ObjectMapper mapper=new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(Map.of("error", "Error_access"));

		
		response.setContentType("application/json; ");
		PrintWriter pw=response.getWriter();
		pw.println(jsonStr);
		pw.close();

		

	}

}
