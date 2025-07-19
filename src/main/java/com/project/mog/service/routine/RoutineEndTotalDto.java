package com.project.mog.service.routine;

import java.time.LocalDateTime;
import java.util.List;

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
	private LocalDateTime t_start;
	private LocalDateTime t_end;
	private RoutineEntity routine;
	private RoutineEndDetailEntity routineEndDetail;
	private RoutineResultEntity routineResult;
	
	
	public RoutineEndTotalEntity toEntity() {
		return RoutineEndTotalEntity.builder()
				.retId(retId)
				.t_start(t_start)
				.t_end(t_end)
				.routine(routine)
				.routineEndDetail(routineEndDetail)
				.routineResult(routineResult)
				.build();
	}
}
