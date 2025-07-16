package com.project.mog.service.routine;

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
public class RoutineResultDto {
	private long rrId;
	private long muscle;
	private long kcal;
	private long reSet;
	private long setNum;
	private long volum;
	private long rouTime;
	private long exVolum;
	
	public RoutineResultEntity toEntity() {
		System.out.println(rrId);
		return RoutineResultEntity.builder()
								.muscle(muscle)
								.kcal(kcal)
								.reSet(reSet)
								.setNum(setNum)
								.volum(exVolum)
								.rouTime(rouTime)
								.exVolum(exVolum)
								.build();
	}
	
	public static RoutineResultDto toDto(RoutineResultEntity rrEntity) {
		if(rrEntity==null) return null;
		return RoutineResultDto.builder()
								.rrId(rrEntity.getRrId())
								.muscle(rrEntity.getMuscle())
								.kcal(rrEntity.getKcal())
								.reSet(rrEntity.getReSet())
								.volum(rrEntity.getVolum())
								.rouTime(rrEntity.getRouTime())
								.exVolum(rrEntity.getExVolum())
								.build();
	}
}
