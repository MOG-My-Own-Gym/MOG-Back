package com.project.mog.controller.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialLoginRequest {
	@Schema(description = "socialType",example="kakao")
	private String socialType;
	@Schema(description = "accessToken",example="AccessToken")
	private String accessToken;
}
