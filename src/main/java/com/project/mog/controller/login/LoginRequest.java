package com.project.mog.controller.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	@Schema(description = "email",example="test@test.com")
	private String email;
	@Schema(description = "password",example="testuser1")
	private String password;
}
