package com.project.mog.controller.routine;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoutineEndTotalRequest {
	LocalDateTime startDate;
	LocalDateTime endDate;
}
