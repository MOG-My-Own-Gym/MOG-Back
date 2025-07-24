package com.project.mog.repository.routine;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="routineresult")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineResultEntity {
	@Id
	@Column(length=19,nullable=false)
	@SequenceGenerator(name = "SEQ_RR_GENERATOR",sequenceName = "SEQ_RR",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="SEQ_RR_GENERATOR" )	
	private long rrId;
	@Column(length = 19)
	private long muscle;
	@Column(length = 19)
	private long kcal;
	@Column(length = 19)
	private long reSet;
	@Column(length = 19)
	private long setNum;
	@Column(length = 19)
	private long volum;
	@Column(length = 19)
	private long rouTime;
	@Column(length = 19)
	private long exVolum;
}
