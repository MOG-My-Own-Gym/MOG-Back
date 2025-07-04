package com.project.mog.repository.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.mog.repository.bios.BiosEntity;

public interface UsersRepository extends JpaRepository<UsersEntity, Long>{

	Optional<UsersEntity> findById(Long usersId);
}