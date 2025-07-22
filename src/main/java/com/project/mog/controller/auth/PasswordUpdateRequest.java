package com.project.mog.controller.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateRequest {
	private String originPassword;
	private String newPassword;
}
