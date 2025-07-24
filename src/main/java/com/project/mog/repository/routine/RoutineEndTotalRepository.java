package com.project.mog.repository.routine;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoutineEndTotalRepository extends JpaRepository<RoutineEndTotalEntity, Long> {

	@Query(nativeQuery=true, value="SELECT * FROM ROUTINEENDTOTAL WHERE SETID=?1")
	List<RoutineEndTotalEntity> findAllBySetId(long setId);
	
	@Query(nativeQuery=true, value="SELECT * FROM ROUTINEENDTOTAL WHERE SETID=?1 AND TSTART<=?3 AND TEND>=?2")
	List<RoutineEndTotalEntity> findAllBySetIdAndDateBetween(long setId,LocalDateTime startDate, LocalDateTime endDate);

}
