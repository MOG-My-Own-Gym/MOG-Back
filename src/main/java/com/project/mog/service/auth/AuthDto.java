package com.project.mog.service.auth;

import java.time.LocalDateTime;

import com.project.mog.repository.auth.AuthEntity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthDto {
	private long authId;
	private String password;
	private LocalDateTime connectTime;
	
	public AuthEntity toEntity() {
			return AuthEntity.builder()
					.authId(authId)
					.password(password)
					.connectTime(connectTime)
					.build();
	}
	
	public static AuthDto toDto(AuthEntity aEntity) {
		if(aEntity==null) return null;	
		return AuthDto.builder()
				.authId(aEntity.getAuthId())
				.password(aEntity.getPassword())
				.connectTime(aEntity.getConnectTime())
				.build();
	}

}
