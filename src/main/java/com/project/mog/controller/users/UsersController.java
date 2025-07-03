package com.project.mog.controller.users;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.mog.repository.users.UsersEntity;
import com.project.mog.service.users.UsersDto;
import com.project.mog.service.users.UsersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users/")
@RequiredArgsConstructor
public class UsersController {
	private final UsersService usersService;
	
	@GetMapping("list")
	public ResponseEntity<List<UsersDto>> getAllUsers(){
		List<UsersDto> users = usersService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	@PostMapping("signup")
	public ResponseEntity<UsersDto> createUser(@RequestBody UsersDto usersDto){
		UsersDto createUsers = usersService.createUser(usersDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createUsers);
	}
	@GetMapping("/{usersId}")
	public ResponseEntity<UsersDto> getUser(@PathVariable Long usersId){
		return usersService.getUser(usersId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
				
	}
	@DeleteMapping("/delete/{usersId}")
	public ResponseEntity<UsersDto> deleteUser(@PathVariable Long usersId){
		UsersDto deleteUsers = usersService.deleteUser(usersId);
		return ResponseEntity.status(HttpStatus.OK).body(deleteUsers);
	}
	
	
}
