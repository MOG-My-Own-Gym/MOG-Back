package com.project.mog.repository.routine;

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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="saveroutine")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveRoutineEntity {
	@Id
	@Column(length=19,nullable=false)
	@SequenceGenerator(name = "SEQ_SR_GENERATOR",sequenceName = "SEQ_SR",allocationSize = 1,initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="SEQ_SR_GENERATOR" )	
	private long srId;
	@Column(length=19)
	private long exId;
	@Column(length=200)
	private String srName;
	
	@Column(length=19)
	private long reps;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "setId", referencedColumnName = "setId", nullable = true)
	private RoutineEntity routine;
	
	@OneToMany(mappedBy = "saveRoutine",fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<SaveRoutineSetEntity> saveRoutineSet;
	
}
