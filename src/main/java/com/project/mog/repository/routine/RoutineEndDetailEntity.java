package com.project.mog.repository.routine;

import java.time.LocalDateTime;


import jakarta.persistence.CascadeType;
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
@Table(name="routineenddetail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineEndDetailEntity {
	@Id
	@Column(length=19,nullable=false)
	@SequenceGenerator(name = "SEQ_RED_GENERATOR",sequenceName = "SEQ_RED",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="SEQ_RED_GENERATOR" )	
	private long redId;
	
	@Column(length=100)
	private String srName;
	
	@Column(length=19)
	private long setNumber;
	
	@Column(length=19)
	private long reps;
	
	@Column(length=19)
	private long weight;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "retId", nullable = false)
	private RoutineEndTotalEntity routineEndTotal;

}
