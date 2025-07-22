	package com.project.mog.repository.users;

import java.time.LocalDateTime;

import com.project.mog.repository.auth.AuthEntity;
import com.project.mog.repository.bios.BiosEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersEntity {
	@Id
	@Column(length=19,nullable=false)
	@SequenceGenerator(name = "SEQ_USERS_GENERATOR",sequenceName = "SEQ_USERS",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="SEQ_USERS_GENERATOR" )	
	private long usersId;
	
	@Column(length = 10,nullable = false)
	private String usersName;
	
	@Column(name = "NICKNAME",length= 100, nullable = false)
	private String nickName;
	
	@Column(length = 100,nullable = false)
	private String email;
	
	@Column(length = 100,nullable = true)
	private String profileImg;
	
	@Column(nullable=false,updatable = false)
	private LocalDateTime regDate;
	@Column(nullable=false)
	private LocalDateTime updateDate;
	@PrePersist//영속화 되기전 아래 메소드 실행
	public void setCreateDate() {
		this.regDate= LocalDateTime.now();
		this.updateDate=LocalDateTime.now();
	}
	@PreUpdate//영속화 되기전 아래 메소드 실행
	public void setUpdateDate() {
		this.updateDate= LocalDateTime.now();		
	}
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private BiosEntity bios;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private AuthEntity auth;

}
