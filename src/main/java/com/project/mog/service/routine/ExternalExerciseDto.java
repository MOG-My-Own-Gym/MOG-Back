package com.project.mog.service.routine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalExerciseDto {
	private String name;
	private String category;
	private String equipment;
	private String force;
	private String[] instructions;
	private String level;
	private String mechanic;
	private String[] primaryMuscles;
	private String[] secondaryMuscles;
}
