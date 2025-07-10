package com.project.mog.repository.routine;

import java.time.LocalDateTime;

import com.project.mog.repository.auth.AuthEntity;
import com.project.mog.repository.bios.BiosEntity;
import com.project.mog.repository.users.UsersEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="routinemain")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineEntity {
	@Id
	@Column(length=19,nullable=false)
	@SequenceGenerator(name = "SEQ_ROUTINE_GENERATOR",sequenceName = "SEQ_ROUTINE",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="SEQ_ROUTINE_GENERATOR" )	
	private long setId;
	
	@Column(length=200)
	private String routineName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usersId", referencedColumnName = "usersId", nullable = true)
	private UsersEntity user;
}
