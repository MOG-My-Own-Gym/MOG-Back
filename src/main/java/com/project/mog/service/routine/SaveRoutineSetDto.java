package com.project.mog.service.routine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
public class SaveRoutineSetDto {
	private long srsId;
	private long weight;
	private long many;
	
	public SaveRoutineSetEntity toEntity() {
		return SaveRoutineSetEntity.builder()
				.weight(weight)
				.many(many)
				.build();
	}
	
	public static SaveRoutineSetDto toDto(SaveRoutineSetEntity saveRoutineSetEntity) {
		if(saveRoutineSetEntity==null) return null;
		return SaveRoutineSetDto.builder()
				.weight(saveRoutineSetEntity.getWeight())
				.many(saveRoutineSetEntity.getMany())
				.build();
	}
	
	public static List<SaveRoutineSetDto> toDtoList(List<SaveRoutineSetEntity> entities) {
	    if (entities == null) return Collections.emptyList();

	    List<SaveRoutineSetDto> result = new ArrayList<>();
	    for (int i = 0; i < entities.size(); i++) {
	        SaveRoutineSetEntity e = entities.get(i);
	        result.add(SaveRoutineSetDto.builder()
	            .srsId(i + 1) // 순서 기반 ID 부여
	            .weight(e.getWeight())
	            .many(e.getMany())
	            .build());
	    }
	    return result;
	}

}
