package com.project.mog.repository.routine;

import java.time.LocalDateTime;
import java.util.List;

import com.project.mog.repository.users.UsersEntity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="routineendtotal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineEndTotalEntity {
	@Id
	@Column(length=19,nullable=false)
	@SequenceGenerator(name = "SEQ_RET_GENERATOR",sequenceName = "SEQ_RET",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="SEQ_RET_GENERATOR" )	
	private long retId;

	private LocalDateTime tStart;
	private LocalDateTime tEnd;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "setId", referencedColumnName = "setId", nullable = true)
	private RoutineEntity routine;
	

	@OneToMany(mappedBy = "routineEndTotal", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RoutineEndDetailEntity> routineEndDetail;
	
	@OneToOne(fetch = FetchType.LAZY)

	@JoinColumn(name="rrId",referencedColumnName="rrId",nullable=true)
	private RoutineResultEntity routineResult;
}
