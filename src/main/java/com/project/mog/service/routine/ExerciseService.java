package com.project.mog.service.routine;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.mog.repository.routine.ExerciseRepository;
import com.project.mog.repository.routine.ExercisesEntity;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ExerciseService {
	private final ExternalExercisesService externalExercisesService;
	private final ExerciseRepository exerciseRepository;
	
	public void fetchAndSave() {
		List<ExternalExerciseDto> externalExercises = externalExercisesService.fetchExercises();
		
		List<ExercisesEntity> exercises = externalExercises.stream().map(dto->{
			ExercisesEntity convertExerciseEntity = ExercisesEntity.builder()
					.exName(dto.getName())
					.exCategory(dto.getCategory())
					.exEquipment(dto.getEquipment())
					.exForce(dto.getForce())
					.exLevel(dto.getLevel())
					.exMechanic(dto.getMechanic())
					.exInstructions(dto.getInstructions())
					.exPrimaryMuscles(dto.getPrimaryMuscles())
					.exSecondaryMuscles(dto.getSecondaryMuscles())
					.build();
			return convertExerciseEntity;
		}).collect(Collectors.toList());
		
		exerciseRepository.saveAll(exercises);
		
	}
}
