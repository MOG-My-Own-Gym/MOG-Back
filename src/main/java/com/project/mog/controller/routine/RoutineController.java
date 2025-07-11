package com.project.mog.controller.routine;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.mog.security.jwt.JwtUtil;
import com.project.mog.service.routine.RoutineDto;
import com.project.mog.service.routine.RoutineService;
import com.project.mog.service.users.UsersDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/routine/")
@RequiredArgsConstructor
public class RoutineController {
	
	private final JwtUtil jwtUtil;
	private final RoutineService routineService;
	
	@GetMapping("list")
	public ResponseEntity<List<RoutineDto>> getAllRoutines(@RequestHeader("Authorization") String authHeader){
		String token = authHeader.replace("Bearer ", "");
		String authEmail = jwtUtil.extractUserEmail(token);
		List<RoutineDto> routines = routineService.getAllRoutines(authEmail);
		return ResponseEntity.ok(routines);
	}
	
	@GetMapping("details/{setId}")
	public ResponseEntity<RoutineDto> getRoutineDetail(@RequestHeader("Authorization") String authHeader, @PathVariable long setId){
		String token = authHeader.replace("Bearer ", "");
		String authEmail = jwtUtil.extractUserEmail(token);
		RoutineDto routine = routineService.getRoutine(authEmail, setId);
		System.out.println(routine);
		return ResponseEntity.ok(routine);
	}
	
	
	@PostMapping("create")
	public ResponseEntity<RoutineDto> createRoutine(@RequestHeader("Authorization") String authHeader, @RequestBody RoutineDto routineDto){
		String token = authHeader.replace("Bearer ", "");
		String authEmail = jwtUtil.extractUserEmail(token);
		RoutineDto routine = routineService.createRoutine(authEmail,routineDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(routine);
	}
}
