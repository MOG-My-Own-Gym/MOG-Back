package com.project.mog.repository.routine;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoutineEndTotalRepository extends JpaRepository<RoutineEndTotalEntity, Long> {

	@Query(nativeQuery=true,value="SELECT * FROM routineendtotal WHERE setid=?1" )
	List<RoutineEndTotalEntity> findAllBySetId(long setId);

}
