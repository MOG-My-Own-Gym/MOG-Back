package com.project.mog.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.mog.repository.users.UsersEntity;

public interface AuthRepository extends JpaRepository<AuthEntity, Long> {
	AuthEntity findByUser(UsersEntity user);
}
