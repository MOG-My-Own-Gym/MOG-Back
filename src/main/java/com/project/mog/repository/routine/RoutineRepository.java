package com.project.mog.repository.routine;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.mog.service.routine.RoutineDto;

public interface RoutineRepository extends JpaRepository<RoutineEntity, Long>{
	
	@Query(nativeQuery=true,value="SELECT * FROM routinemain WHERE usersid =?1")
	public List<RoutineEntity> findByUsersId(Long usersId);

	@Query(nativeQuery=true,value="SELECT * FROM routinemain WHERE usersid =?1 AND setid=?2")
	public Optional<RoutineEntity> findByUsersIdAndSetId(Long usersId, Long setId);
}
