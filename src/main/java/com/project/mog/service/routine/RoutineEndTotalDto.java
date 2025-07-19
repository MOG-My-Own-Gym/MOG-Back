package com.project.mog.service.routine;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.project.mog.repository.routine.RoutineEndDetailEntity;
import com.project.mog.repository.routine.RoutineEndTotalEntity;
import com.project.mog.repository.routine.RoutineEntity;
import com.project.mog.repository.routine.RoutineResultEntity;

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
public class RoutineEndTotalDto {
	private long retId;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
	private LocalDateTime tStart;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
	private LocalDateTime tEnd;
	private List<RoutineEndDetailDto> routineEndDetails;
	private RoutineResultDto routineResult;
	
	
	
	public static RoutineEndTotalDto toDto(RoutineEndTotalEntity retEntity) {
		if(retEntity==null) return null;
		List<RoutineEndDetailEntity> redEntity = retEntity.getRoutineEndDetail();
		RoutineResultEntity rrEntity = retEntity.getRoutineResult();
		List<RoutineEndDetailDto> redDto;
		if(redEntity==null) {
			redDto = List.of();
		}
		else {
			redDto = redEntity.stream().map(RoutineEndDetailDto::toDto).collect(Collectors.toList());
		}
		
		return RoutineEndTotalDto.builder()
								.retId(retEntity.getRetId())
								.tStart(retEntity.getTStart())
								.tEnd(retEntity.getTEnd())
								.routineEndDetails(redDto)
								.routineResult(RoutineResultDto.toDto(rrEntity))
								.build();
	
	}
}

