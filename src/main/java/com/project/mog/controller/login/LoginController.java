package com.project.mog.controller.login;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.mog.service.users.UsersDto;
import com.project.mog.service.users.UsersService;
import com.project.mog.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/v1/users/")
@RequiredArgsConstructor
public class LoginController {
	private final UsersService usersService;
	private final JwtUtil jwtUtil;

	@PostMapping("login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
		String email = usersService.login(request).getEmail();
		
		String accessToken = jwtUtil.generateAccessToken(email);
		String refreshToken = jwtUtil.generateRefreshToken(email);
		
		LoginResponse loginResponse = LoginResponse.builder()
											.email(email)
											.accessToken(accessToken)
											.refreshToken(refreshToken)
											.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
	}
	
	
}
