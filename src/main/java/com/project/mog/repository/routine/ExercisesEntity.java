package com.project.mog.repository.routine;

import java.util.List;

import com.project.mog.repository.users.UsersEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="exercises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExercisesEntity {
	@Id
	@Column(length=19,nullable=false)
	@SequenceGenerator(name = "SEQ_EXERCISES_GENERATOR",sequenceName = "SEQ_EXERCISES",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="SEQ_EXERCISES_GENERATOR" )	
	private Long exId;
	@Column(length=200)
	private String exName;
	@Column(length=200)
	private String exCategory;
	@Column(length=200)
	private String exEquipment;
	@Column(length=200)
	private String exForce;
	@Column(length=200)
	private String exLevel;
	@Column(length=200)
	private String exMechanic;
	@Column(length=2000)
	private String[] exInstructions;
	@Column(length=200)
	private String[] exPrimaryMuscles;
	@Column(length=200)
	private String[] exSecondaryMuscles;
	
	
	
}
