package com.project.mog.service.routine;

import com.project.mog.repository.routine.RoutineEntity;
import com.project.mog.repository.routine.SaveRoutineEntity;

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
public class SaveRoutineDto {
	private long srId;
	private long exId;
	private String srName;
	private long setNumber;
	private long reps;
	private long weight;
	private long setId;
	
	public SaveRoutineEntity toEntity(RoutineEntity rEntity) {
		return SaveRoutineEntity.builder()
				.exId(exId)
				.srName(srName)
				.setNumber(setNumber)
				.reps(reps)
				.weight(weight)
				.routine(rEntity)
				.build();
	}
	
	
	
	public static SaveRoutineDto toDto(SaveRoutineEntity srEntity){
		if (srEntity==null) return null;
		return SaveRoutineDto.builder()
				.srId(srEntity.getSrId())
				.exId(srEntity.getExId())
				.srName(srEntity.getSrName())
				.setNumber(srEntity.getSetNumber())
				.reps(srEntity.getReps())
				.weight(srEntity.getWeight())
				.setId(srEntity.getRoutine().getSetId())
				.build();
	}
}
