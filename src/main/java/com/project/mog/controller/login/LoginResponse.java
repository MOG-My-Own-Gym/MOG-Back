package com.project.mog.controller.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
	private String email;
	private String accessToken;
	private String refreshToken;
}
