package com.project.mog.service.routine;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.mog.repository.routine.RoutineEndDetailRepository;
import com.project.mog.repository.routine.RoutineEndTotalRepository;
import com.project.mog.repository.routine.RoutineRepository;
import com.project.mog.repository.routine.RoutineResultRepository;
import com.project.mog.repository.routine.SaveRoutineRepository;
import com.project.mog.repository.users.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
public class ExternalExercisesService {
	private final RestTemplate restTemplate;
	
	public ExternalExercisesService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	public List<ExternalExerciseDto> fetchExercises(){
		String url = "https://raw.githubusercontent.com/kimbongkum/ict4e/master/exercises.json";
		ResponseEntity<ExternalExerciseApiResponseDto> response = restTemplate.getForEntity(url, ExternalExerciseApiResponseDto.class);
		System.out.println(response.getBody());
		return response.getBody().getExercises();
	}
	
	
	
}
