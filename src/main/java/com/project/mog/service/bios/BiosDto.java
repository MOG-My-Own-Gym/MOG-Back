package com.project.mog.service.bios;

import java.time.LocalDateTime;

import com.project.mog.repository.bios.BiosEntity;
import com.project.mog.repository.users.UsersEntity;
import com.project.mog.service.users.UsersDto;

import jakarta.persistence.Column;
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
public class BiosDto {
	private long bioId;
	private long age;
	private boolean gender;
	private long height;
	private long weight;
	
	public BiosEntity toEntity() {
		
		return BiosEntity.builder()
				.bioId(bioId)
				.age(age)
				.gender(gender)
				.height(height)
				.weight(weight)
				.build();
				
	}
	public static BiosDto toDto(BiosEntity bEntity) {
		if (bEntity==null) return null;
		return BiosDto.builder()
				.bioId(bEntity.getBioId())
				.age(bEntity.getAge())
				.gender(bEntity.isGender())
				.height(bEntity.getHeight())
				.weight(bEntity.getWeight())
				.build();
	}
}
