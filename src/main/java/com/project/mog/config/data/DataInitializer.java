package com.project.mog.config.data;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.project.mog.repository.routine.RoutineEndDetailRepository;
import com.project.mog.repository.routine.RoutineEndTotalRepository;
import com.project.mog.repository.routine.RoutineRepository;
import com.project.mog.repository.routine.RoutineResultRepository;
import com.project.mog.repository.routine.SaveRoutineRepository;
import com.project.mog.repository.users.UsersRepository;
import com.project.mog.service.routine.ExerciseService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer {
	private final ExerciseService exerciseService;

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		exerciseService.fetchAndSave();
	}
	

	
	
	
}
