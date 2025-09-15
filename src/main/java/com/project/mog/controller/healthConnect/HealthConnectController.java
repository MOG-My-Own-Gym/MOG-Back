package com.project.mog.controller.healthConnect;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.mog.security.jwt.JwtUtil;
import com.project.mog.service.healthConnect.HealthConnectDto;
import com.project.mog.service.healthConnect.HealthConnectResponseDto;
import com.project.mog.service.healthConnect.HealthConnectService;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/health")
public class HealthConnectController {
	
	private final JwtUtil jwtUtil;
	private final HealthConnectService healthConnectService;
	
	@PostMapping("")
	public ResponseEntity<HealthConnectResponseDto> receiveHealthConnect(
			@RequestHeader("Authorization") String authHeader,
			@RequestBody HealthConnectDto heDto){
		String token = authHeader.replace("Bearer ", "");
		String eamil = jwtUtil.extractUserEmail(token);
		
		HealthConnectResponseDto response = healthConnectService.saveHealthConnect(heDto, eamil);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{usersId}")
	public ResponseEntity<List<HealthConnectDto>> getHealthConnectDataByEmail(@PathVariable Long usersId){
		List<HealthConnectDto> healthDataList=healthConnectService.getHealthConnectData(usersId);
		return ResponseEntity.ok(healthDataList);
	}

}
