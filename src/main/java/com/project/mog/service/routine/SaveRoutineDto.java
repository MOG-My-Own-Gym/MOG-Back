package com.project.mog.service.routine;

import java.util.List;
import java.util.stream.Collectors;

import com.project.mog.repository.routine.RoutineEntity;
import com.project.mog.repository.routine.SaveRoutineEntity;
import com.project.mog.repository.routine.SaveRoutineSetEntity;

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
	private long reps;
	private long setId;
	private List<SaveRoutineSetDto> set;
	
	public SaveRoutineEntity toEntity(RoutineEntity rEntity) {
		return SaveRoutineEntity.builder()
				.exId(exId)
				.srName(srName)
				.reps(reps)
				.saveRoutineSet(set.stream().map(srs->srs.toEntity()).toList())
				.routine(rEntity)
				.build();
	}
	
	
	
	public static SaveRoutineDto toDto(SaveRoutineEntity srEntity){
	    if (srEntity == null) return null;

	    return SaveRoutineDto.builder()
	        .srId(srEntity.getSrId())
	        .exId(srEntity.getExId())
	        .srName(srEntity.getSrName())
	        .set(SaveRoutineSetDto.toDtoList(srEntity.getSaveRoutineSet())) // 🔥 리스트 순서대로 변환
	        .reps(srEntity.getReps())
	        .setId(srEntity.getRoutine().getSetId())
	        .build();
	}
}
