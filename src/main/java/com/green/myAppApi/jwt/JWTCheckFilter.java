package com.green.myAppApi.jwt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JWTCheckFilter extends OncePerRequestFilter{
	
	
	private  final JWTUtil jWTUtil;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		
		String path=request.getRequestURI();
		
		log.info("-->uri:"+path);
		if(request.getMethod().equals("OPTIONS"))return true;
		if(path.startsWith("/api/member/"))return true;  //url을 매번 바꿔서 넣을수 없으니 개선 필요
		return false;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		log.info("------------------- JWTCheckFilter --------------------");
		String auth=request.getHeader("Authorization");
		
		try {
			
			String accessToken=auth.substring(7);
			Map<String, Object>claims=jWTUtil.validateToken(accessToken);
			log.info("claims------------"+claims);
			filterChain.doFilter(request, response);
			
			
		} catch (Exception e) {
			ObjectMapper mapper=new ObjectMapper();
			String errMsg = mapper.writeValueAsString(Map.of("error", "에러 발생 Error access token"));

			
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter pw=response.getWriter();
			pw.println(errMsg);
			pw.close();
			
		}
		
		
		
		
		
	}

}
