package com.project.mog.service.routine;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.mog.annotation.UserAuthorizationCheck;
import com.project.mog.repository.routine.RoutineEntity;
import com.project.mog.repository.routine.RoutineRepository;
import com.project.mog.repository.users.UsersEntity;
import com.project.mog.repository.users.UsersRepository;
import com.project.mog.service.users.UsersDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoutineService {
	
	private final UsersRepository usersRepository;
	private final RoutineRepository routineRepository;
	
	public RoutineEntity toEntity(UsersEntity uEntity, RoutineDto routineDto) {
		RoutineEntity rEntity = RoutineEntity.builder()
								.routineName(routineDto.getRoutineName())
								.user(uEntity)
								.build();
		return rEntity;
	}

	public List<RoutineDto> getAllRoutines(String authEmail) {
		UsersEntity currentUser = usersRepository.findByEmail(authEmail);
		return routineRepository.findByUsersId(currentUser.getUsersId()).stream().map(RoutineDto::toDto).collect(Collectors.toList());
	}
	
	public RoutineDto getRoutine(String authEmail, Long setId) {
		UsersEntity currentUser = usersRepository.findByEmail(authEmail);
		RoutineEntity routineEntity = routineRepository.findByUsersIdAndSetId(currentUser.getUsersId(),setId).orElseThrow(()->new IllegalArgumentException("해당 루틴 정보가 없습니다"));
		return RoutineDto.toDto(routineEntity); 
	}

	public RoutineDto createRoutine(String authEmail,RoutineDto routineDto) {
		UsersEntity uEntity = usersRepository.findByEmail(authEmail);
		RoutineEntity routineEntity = routineRepository.save(toEntity(uEntity,routineDto));
		return RoutineDto.toDto(routineEntity);
	}

}
