package com.project.mog.controller.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.mog.controller.login.LoginRequest;
import com.project.mog.controller.login.LoginResponse;
import com.project.mog.controller.login.SocialLoginRequest;
import com.project.mog.docs.UsersControllerDocs;
import com.project.mog.repository.users.UsersEntity;
import com.project.mog.security.jwt.JwtUtil;
import com.project.mog.service.users.UsersDto;
import com.project.mog.service.users.UsersInfoDto;
import com.project.mog.service.users.UsersService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/v1/users/")
@RequiredArgsConstructor
public class UsersController implements UsersControllerDocs{
	private final JwtUtil jwtUtil;
	private final UsersService usersService;
	
	
	@GetMapping("list")
	public ResponseEntity<List<UsersInfoDto>> getAllUsers(){
		List<UsersInfoDto> users = usersService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	@PostMapping("signup")
	public ResponseEntity<UsersDto> createUser(@RequestBody UsersDto usersDto){
		UsersDto createUsers = usersService.createUser(usersDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createUsers);
	}
	@GetMapping("/{usersId}")
	public ResponseEntity<UsersInfoDto> getUser(@PathVariable Long usersId){
		return usersService.getUser(usersId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	@GetMapping("/email/{email}")
	public ResponseEntity<UsersInfoDto> getUserByEmail(@PathVariable String email){
		return usersService.getUserByEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	@Transactional
	@PutMapping("/update/{usersId}")
	public ResponseEntity<UsersInfoDto> editUser(@RequestHeader("Authorization") String authHeader, @PathVariable Long usersId,@RequestBody UsersInfoDto usersInfoDto){
		String token = authHeader.replace("Bearer ", "");
		String authEmail = jwtUtil.extractUserEmail(token);
		UsersInfoDto editUsers = usersService.editUser(usersInfoDto,usersId,authEmail);
		return ResponseEntity.status(HttpStatus.OK).body(editUsers);
	}
	@Transactional
	@DeleteMapping("/delete/{usersId}")
	public ResponseEntity<UsersInfoDto> deleteUser(@RequestHeader("Authorization") String authHeader, @PathVariable Long usersId){
		String token = authHeader.replace("Bearer ", "");
		String authEmail = jwtUtil.extractUserEmail(token);
		UsersInfoDto deleteUsers = usersService.deleteUser(usersId,authEmail);
		return ResponseEntity.status(HttpStatus.OK).body(deleteUsers);
	}
	
	
	@PostMapping("login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
		UsersDto usersDto = usersService.login(request);
		
		long usersId = usersDto.getUsersId();
		String email = usersDto.getEmail();
		String accessToken = jwtUtil.generateAccessToken(email);
		String refreshToken = jwtUtil.generateRefreshToken(email);
		
		
		LoginResponse loginResponse = LoginResponse.builder()
											.usersId(usersId)
											.email(email)
											.accessToken(accessToken)
											.refreshToken(refreshToken)
											.build();
		
		return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
	}
	

	@PostMapping("login/kakao")
	public ResponseEntity<LoginResponse> socialLogin(@RequestBody SocialLoginRequest request){
		UsersDto usersDto = usersService.socialLogin(request);
		long usersId = usersDto.getUsersId();
		String email = usersDto.getEmail();
		String accessToken = jwtUtil.generateAccessToken(email);
		String refreshToken = jwtUtil.generateRefreshToken(email);
		
		LoginResponse loginResponse = LoginResponse.builder()
										.usersId(usersId)
										.email(email)
										.accessToken(accessToken)
										.refreshToken(refreshToken)
										.build();
		return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
	}
	
}
