package com.project.mog.repository.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.mog.repository.bios.BiosEntity;

public interface UsersRepository extends JpaRepository<UsersEntity, Long>{

	Optional<UsersEntity> findById(Long usersId);

	@Query(nativeQuery = true,value = "SELECT USERS.* FROM USERS JOIN AUTH ON (USERS.USERSID=AUTH.USERSID) WHERE USERS.EMAIL=?1 AND AUTH.PASSWORD=?2")
	Optional<UsersEntity> findByEmailAndPassword(String email, String password);

	@Query(nativeQuery = true,value="SELECT * FROM USERS WHERE USERS.EMAIL=?1")
	UsersEntity findByEmail(String email);
}