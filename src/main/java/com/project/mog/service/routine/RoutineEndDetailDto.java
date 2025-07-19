package com.project.mog.service.routine;

import com.project.mog.repository.routine.RoutineEndDetailEntity;
import com.project.mog.repository.routine.RoutineEndTotalEntity;

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
public class RoutineEndDetailDto {
	private long redId;
	private String srName;
	private long setNumber;
	private long reps;
	private long weight;
	private RoutineEndTotalEntity routineEndTotalEntity;
	
	public RoutineEndDetailEntity toEntity(RoutineEndTotalEntity retEntity) {
		return RoutineEndDetailEntity.builder()
									.srName(srName)
									.setNumber(setNumber)
									.reps(reps)
									.weight(weight)
									.routineEndTotal(retEntity)
									.build();
	}
	
	public static RoutineEndDetailDto toDto(RoutineEndDetailEntity redEntity) {
		if(redEntity==null) return null;
		return RoutineEndDetailDto.builder()
								.redId(redEntity.getRedId())
								.srName(redEntity.getSrName())
								.setNumber(redEntity.getSetNumber())
								.reps(redEntity.getReps())
								.weight(redEntity.getWeight())
								.build();
	}
}