package com.project.mog.service.routine;

import com.project.mog.repository.routine.RoutineEntity;
import com.project.mog.service.bios.BiosDto;
import com.project.mog.service.users.UsersDto;

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
public class RoutineDto {
	private long setId;
	private String routineName;
	private long usersId;
	
	
	
	public static RoutineDto toDto(RoutineEntity rEntity) {
		if(rEntity==null) return null;
		return RoutineDto.builder()
				.setId(rEntity.getSetId())
				.routineName(rEntity.getRoutineName())
				.usersId(rEntity.getUser().getUsersId())
				.build();
	}
}
