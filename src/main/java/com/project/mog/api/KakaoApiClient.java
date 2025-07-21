package com.project.mog.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.project.mog.service.users.KakaoUser;

@Component
public class KakaoApiClient {
	private final RestTemplate restTemplate;
	
	public KakaoApiClient() {
		this.restTemplate = new RestTemplate();
	}
	
	public KakaoUser getUserInfo(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+accessToken);
		
		HttpEntity<Void> request = new HttpEntity<>(headers);
		
		ResponseEntity<KakaoUser> response = restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET,
				request,KakaoUser.class);
		System.out.println("accessToken authorized");
		return response.getBody();
	}
}
