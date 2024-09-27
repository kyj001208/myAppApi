package com.green.myAppApi.jwt;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.green.myAppApi.domain.entity.MemberRole;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JWTUtil {
	
	private SecretKey secreatkey;
	
	public JWTUtil(@Value("${jwt.secret-key}") String key) {
		
		this.secreatkey=new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), 
				Jwts.SIG.HS256.key().build().getAlgorithm());
		
	}
	
	
	
	public String generateToken(Map<String, Object> claims, long expiredMs) {
	    
	    // JWT 토큰 생성 시작
	    return Jwts.builder()
	            // Optional: JWT 헤더에 "typ" 필드를 추가 (JWT 유형)
	            //.setHeader(Map.of("typ","JWP")) // 다른 방식으로 헤더를 설정할 수 있음
	            .issuer("myApp") // 토큰 발급자 설정
	            .header() // 헤더 설정 시작
	            .add("typ", "JWT") // "typ" 필드에 "JWT" 값 추가
	            .and() // 헤더 설정 완료
	            
	            .claims(claims) // 클레임 추가 (사용자 정보 등)
	            .subject((String) claims.get("email")) // 토큰의 주체 설정 (이메일 등)
	            //.issuedAt(new Date(System.currentTimeMillis())) // 발급일 설정 (현재 시간)
	            .issuedAt(Date.from(ZonedDateTime.now().toInstant())) // 발급일을 현재 시간으로 설정
	            
	            // 만료 시간 설정 (현재 시간에 만료 시간 추가)
	            .expiration(Date.from(ZonedDateTime.now().plusMinutes(expiredMs).toInstant()))
	            
	            // 비밀 키로 서명
	            .signWith(secreatkey) // 비밀 키로 서명
	            .compact(); // 토큰을 문자열로 변환하여 반환
	}



	public Map<String, Object> validateToken(String token) {
		
		Map<String, Object> claims=null;
		try {
			
			claims=Jwts.parser()
					.verifyWith(secreatkey)
					.build()
					.parseSignedClaims(token)
					.getPayload();
					
			
		}catch(MalformedJwtException malformedJwtException){
	        throw new CustomJWTException("MalFormed");
	    }catch(ExpiredJwtException expiredJwtException){
	        throw new CustomJWTException("Expired");
	    }catch(InvalidClaimException invalidClaimException){
	        throw new CustomJWTException("Invalid");
	    }catch(JwtException jwtException){
	        throw new CustomJWTException("JWTError");
	    }catch(Exception e){
	        throw new CustomJWTException("Error");
	    }
		return claims;
	}
	
	
	public Boolean isExpired(String token) {
		
		
		return Jwts.parser().verifyWith(secreatkey).build().parseSignedClaims(token).getPayload()
				.getExpiration().before(new Date());
	}
	
	public Object getRoles(String token) {
		
		
		return Jwts.parser().verifyWith(secreatkey).build().parseSignedClaims(token).getPayload()
				.get("memberRoles",Object.class);
	}


}
