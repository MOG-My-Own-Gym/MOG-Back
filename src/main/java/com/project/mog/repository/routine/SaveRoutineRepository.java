package com.project.mog.repository.routine;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaveRoutineRepository extends JpaRepository<SaveRoutineEntity, Long> {

	@Query(nativeQuery = true,value = "SELECT * FROM SAVEROUTINE WHERE SETID=?1")
	List<SaveRoutineEntity> findAllBySetId(Long setId);

}
