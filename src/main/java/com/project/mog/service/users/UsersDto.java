package com.project.mog.service.users;

import java.time.LocalDateTime;

import com.project.mog.repository.bios.BiosEntity;
import com.project.mog.repository.users.UsersEntity;
import com.project.mog.service.bios.BiosDto;

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
public class UsersDto {
	private long usersId;
	private BiosDto biosDto;
	private String usersName;
	private String email;
	private String profileImg;
	private LocalDateTime regDate;
	private LocalDateTime updateDate;
	
	public UsersEntity toEntity() {
		UsersEntity uEntity = UsersEntity.builder()
					.usersId(usersId)
					.usersName(usersName)
					.email(email)
					.profileImg(profileImg)
					.regDate(regDate)
					.updateDate(updateDate)
					.bios(biosDto.toEntity())
					.build();
		if(biosDto!=null) {
			BiosEntity bEntity = biosDto.toEntity();
			bEntity.setUser(uEntity);
			uEntity.setBios(bEntity);
		}
		return uEntity;
	}
	
	public static UsersDto toDto(UsersEntity uEntity) {
		if (uEntity==null) return null;
		return UsersDto.builder()
				.usersId(uEntity.getUsersId())
				.usersName(uEntity.getUsersName())
				.email(uEntity.getEmail())
				.profileImg(uEntity.getProfileImg())
				.regDate(uEntity.getRegDate())
				.updateDate(uEntity.getUpdateDate())
				.biosDto(BiosDto.toDto(uEntity.getBios()))
				.build();
	}

}
