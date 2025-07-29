package com.project.mog.service.routine;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.mog.annotation.UserAuthorizationCheck;
import com.project.mog.controller.routine.RoutineEndTotalRequest;
import com.project.mog.repository.routine.RoutineEndDetailEntity;
import com.project.mog.repository.routine.RoutineEndDetailRepository;
import com.project.mog.repository.routine.RoutineEndTotalEntity;
import com.project.mog.repository.routine.RoutineEndTotalRepository;
import com.project.mog.repository.routine.RoutineEntity;
import com.project.mog.repository.routine.RoutineRepository;
import com.project.mog.repository.routine.RoutineResultEntity;
import com.project.mog.repository.routine.RoutineResultRepository;
import com.project.mog.repository.routine.SaveRoutineEntity;
import com.project.mog.repository.routine.SaveRoutineRepository;
import com.project.mog.repository.routine.SaveRoutineSetEntity;
import com.project.mog.repository.users.UsersEntity;
import com.project.mog.repository.users.UsersRepository;
import com.project.mog.service.users.UsersDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoutineService {
	
	private final UsersRepository usersRepository;
	private final RoutineRepository routineRepository;
	private final SaveRoutineRepository saveRoutineRepository;
	private final RoutineEndTotalRepository routineEndTotalRepository;
	private final RoutineEndDetailRepository routineEndDetailRepository;
	private final RoutineResultRepository routineResultRepository;
	
	public RoutineEntity toEntity(UsersEntity uEntity, RoutineDto routineDto) {
		RoutineEntity rEntity = RoutineEntity.builder()
								.routineName(routineDto.getRoutineName())
								.user(uEntity)
								.build();
		rEntity.setSaveRoutine(routineDto.getSaveRoutineDto().stream().map(srDto->{
			SaveRoutineEntity srEntity = srDto.toEntity(rEntity);
			srEntity.setSaveRoutineSet(srDto.getSet().stream().map(srs->{
				return SaveRoutineSetEntity.builder().weight(srs.getWeight()).many(srs.getMany()).saveRoutine(srEntity).build();
			}).toList());
			return srEntity;
			}).collect(Collectors.toList()));
		return rEntity;
	}
	public RoutineEndTotalEntity toEntity(RoutineEndTotalDto retDto, Long setId) {
		RoutineEntity rEntity = routineRepository.findById(setId).orElseThrow(()->new IllegalArgumentException("루틴을 찾을 수 없습니다"));
		RoutineEndTotalEntity retEntity = RoutineEndTotalEntity.builder()
				.routineResult(retDto.getRoutineResult().toEntity())
				.build();
		List<RoutineEndDetailEntity> redEntity = retDto.getRoutineEndDetails().stream()
													.map(detailDto -> detailDto.toEntity(retEntity))
													.collect(Collectors.toList());
		retEntity.setRoutineEndDetail(redEntity);
		return retEntity;
	}
	

	public List<RoutineDto> getAllRoutines(String authEmail) {
		UsersEntity currentUser = usersRepository.findByEmail(authEmail).orElseThrow(()->new IllegalArgumentException("유효하지 않은 사용자입니다"));
		return routineRepository.findByUsersId(currentUser.getUsersId()).stream().map(RoutineDto::toDto).collect(Collectors.toList());
	}
	
	public RoutineDto getRoutine(String authEmail, Long setId) {
		UsersEntity currentUser = usersRepository.findByEmail(authEmail).orElseThrow(()->new IllegalArgumentException("유효하지 않은 사용자입니다"));
		RoutineEntity routineEntity = routineRepository.findByUsersIdAndSetId(currentUser.getUsersId(),setId).orElseThrow(()->new IllegalArgumentException("해당 루틴 정보가 없습니다"));
		List<SaveRoutineEntity> saveRoutineEntity = saveRoutineRepository.findAllBySetId(setId);
		routineEntity.setSaveRoutine(saveRoutineEntity);
		return RoutineDto.toDto(routineEntity); 
	}

	public RoutineDto createRoutine(String authEmail, RoutineDto routineDto) {
		UsersEntity uEntity = usersRepository.findByEmail(authEmail).orElseThrow(()->new IllegalArgumentException("유효하지 않은 사용자입니다"));
		RoutineEntity routineEntity = routineRepository.save(toEntity(uEntity,routineDto));
		return RoutineDto.toDto(routineEntity);
	}
	
	public RoutineDto updateRoutine(String authEmail,Long setId, RoutineDto routineDto) {
		UsersEntity uEntity = usersRepository.findByEmail(authEmail)
			    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다"));

			RoutineEntity routineEntity = routineRepository.findById(routineDto.getSetId())
			    .orElseThrow(() -> new IllegalArgumentException("루틴이 존재하지 않습니다"));

			// 루틴 이름 업데이트
			routineEntity.setRoutineName(routineDto.getRoutineName());

			// 기존 연관관계 초기화 (선택적)
			routineEntity.getSaveRoutine().clear();
			
			// 새로운 SaveRoutineEntity 리스트 생성
			List<SaveRoutineEntity> saveRoutineEntities = routineDto.getSaveRoutineDto().stream()
			    .map(saveRoutineDto -> {
			        SaveRoutineEntity saveRoutineEntity = new SaveRoutineEntity();
			        saveRoutineEntity.setSrName(saveRoutineDto.getSrName());
			        saveRoutineEntity.setExId(saveRoutineDto.getExId());
			        saveRoutineEntity.setReps(saveRoutineDto.getReps());
			        saveRoutineEntity.setRoutine(routineEntity); // 연관관계 주입

			        // 세트 매핑
			        List<SaveRoutineSetEntity> sets = saveRoutineDto.getSet().stream()
			            .map(setDto -> {
			            	SaveRoutineSetEntity setEntity = new SaveRoutineSetEntity();
			                setEntity.setWeight(setDto.getWeight());
			                setEntity.setMany(setDto.getMany());
			                setEntity.setSaveRoutine(saveRoutineEntity); // 연관관계 주입
			                return setEntity;
			            })
			            .collect(Collectors.toList());

			        saveRoutineEntity.setSaveRoutineSet(sets);// 연관관계 설정
			        return saveRoutineEntity;
			    })
			    .collect(Collectors.toList());

			// 루틴에 연결
			routineEntity.getSaveRoutine().addAll(saveRoutineEntities);


			routineRepository.save(routineEntity);
			
			return RoutineDto.toDto(routineEntity);

	}
	
	public SaveRoutineDto getSaveRoutine(Long setId, Long srId) {
		SaveRoutineEntity saveRoutineEntity = saveRoutineRepository.findBySetIdAndSrId(setId,srId);
		
		return SaveRoutineDto.toDto(saveRoutineEntity);
	}

	
	public SaveRoutineDto createSaveRoutine(SaveRoutineDto saveRoutineDto, Long setId) {
		RoutineEntity routineEntity = routineRepository.findById(setId).orElseThrow(()->new IllegalArgumentException("루틴을 찾을 수 없습니다"));
		SaveRoutineEntity saveRoutineEntity = saveRoutineDto.toEntity(routineEntity);
		saveRoutineRepository.save(saveRoutineEntity);
		return SaveRoutineDto.toDto(saveRoutineEntity);
	}

	public SaveRoutineDto deleteSaveRoutine(Long srId) {
		SaveRoutineEntity saveRoutineEntity = saveRoutineRepository.findById(srId).orElseThrow(()->new IllegalArgumentException("삭제할 루틴 상세를 찾을 수 없습니다"));
		saveRoutineRepository.deleteById(srId);
		return SaveRoutineDto.toDto(saveRoutineEntity);
	}

	public RoutineEndTotalDto createRoutineEndTotal(RoutineEndTotalDto retDto, Long setId) {
		RoutineEntity routineEntity = routineRepository.findById(setId).orElseThrow(()->new IllegalArgumentException("루틴을 찾을 수 없습니다"));
		
		RoutineResultEntity rrEntity = retDto.getRoutineResult()!=null?retDto.getRoutineResult().toEntity():null;
		if(rrEntity!=null)routineResultRepository.save(rrEntity);
		RoutineEndTotalEntity retEntity = RoutineEndTotalEntity.builder()
											.tStart(retDto.getTStart())
											.tEnd(retDto.getTEnd())
											.routine(routineEntity)
											.routineResult(rrEntity)
											.build();
		List<RoutineEndDetailEntity> redEntity = retDto.getRoutineEndDetails().stream().map(red->{red.setRoutineEndTotalEntity(retEntity);return red.toEntity(retEntity);}).collect(Collectors.toList());
		retEntity.setRoutineEndDetail(redEntity);
		routineEndTotalRepository.save(retEntity);
		return RoutineEndTotalDto.toDto(retEntity);
	}
	
	public List<RoutineEndTotalDto> getRoutineEndTotal(String authEmail, RoutineEndTotalRequest routineEndTotalRequest) {
		UsersEntity userEntity = usersRepository.findByEmail(authEmail).orElseThrow(()->new IllegalArgumentException("유효하지 않은 사용자입니다"));
		List<RoutineEntity> routineEntities = routineRepository.findByUsersId(userEntity.getUsersId());
		List<RoutineEndTotalEntity> routineEndTotalEntities = routineEndTotalRequest==null?
																routineEntities.stream().flatMap(routine->routineEndTotalRepository.findAllBySetId(routine.getSetId()).stream()).collect(Collectors.toList())
																:routineEntities.stream().flatMap(routine->routineEndTotalRepository.findAllBySetIdAndDateBetween(routine.getSetId(),routineEndTotalRequest.getStartDate(),routineEndTotalRequest.getEndDate()).stream()).collect(Collectors.toList());
				
		return routineEndTotalEntities.stream().map(RoutineEndTotalDto::toDto).collect(Collectors.toList());
	}
	
	
}
