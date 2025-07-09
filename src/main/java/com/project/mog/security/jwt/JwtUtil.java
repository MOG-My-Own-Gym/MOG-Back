package com.project.mog.security.jwt;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;


@Component
public class JwtUtil {
	
	@Value("${jwttoken.secret-key}")
	private String secretKey;
	
	private SecretKey key; 
	
	@PostConstruct
	public void initKey() {
		byte[] decoded = Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(decoded);
	}
	
	
	private final long ACCESS_TOKEN_EXPIRE = 1000*60*60*15;
	private final long REFRESH_TOKEN_EXPIRE = 1000L*60*60*24*24;
	
	public String generateAccessToken(String email) {
		return generateToken(email,ACCESS_TOKEN_EXPIRE);
	}
	public String generateRefreshToken(String email) {
		return generateToken(email,REFRESH_TOKEN_EXPIRE);
	}
	
	private String generateToken(String email, long exp) {
		Date now = new Date();
		
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime()+exp))
				.signWith(key,SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String extractUserEmail(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
				
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String email = extractUserEmail(token);
		return email.equals(userDetails.getUsername());
		
	}
	
	
}
