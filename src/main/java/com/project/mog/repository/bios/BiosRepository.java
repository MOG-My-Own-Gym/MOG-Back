package com.project.mog.repository.bios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.project.mog.repository.users.UsersEntity;



public interface BiosRepository extends JpaRepository<BiosEntity, Long> {
	
	BiosEntity findByUser(UsersEntity user);
}
