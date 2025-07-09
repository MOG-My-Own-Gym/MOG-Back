package com.project.mog.service.users;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.project.mog.controller.login.LoginRequest;
import com.project.mog.controller.login.LoginResponse;
import com.project.mog.repository.auth.AuthEntity;
import com.project.mog.repository.auth.AuthRepository;
import com.project.mog.repository.bios.BiosEntity;
import com.project.mog.repository.bios.BiosRepository;
import com.project.mog.repository.users.UsersEntity;
import com.project.mog.repository.users.UsersRepository;

@Service
public class UsersService {

		private UsersRepository usersRepository;
		private BiosRepository biosRepository;
		private AuthRepository authRepository;
		
		
		@Autowired
		public UsersService(UsersRepository usersRepository, BiosRepository biosRepository,AuthRepository authRepository ) {
			this.usersRepository=usersRepository;
			this.biosRepository=biosRepository;
			this.authRepository=authRepository;
		}


		public List<UsersDto> getAllUsers() {
			return usersRepository.findAll().stream().map(UsersDto::toDto).collect(Collectors.toList());
		}


		public UsersDto createUser(UsersDto usersDto) {
			UsersEntity isDuplicated = usersRepository.findByEmail(usersDto.getEmail());
			if(isDuplicated!=null) throw new IllegalArgumentException("중복된 아이디입니다");
			UsersEntity uEntity = usersRepository.save(usersDto.toEntity());
			
			return UsersDto.toDto(uEntity);
		}


		public Optional<UsersDto> getUser(Long usersId) {
			return usersRepository.findById(usersId).map(uEntity->UsersDto.toDto(uEntity));
		}


		public UsersDto deleteUser(Long usersId, String authEmail) {
			UsersEntity currentUser = usersRepository.findByEmail(authEmail);
			UsersEntity targetUser = usersRepository.findById(usersId).orElseThrow(()->new RuntimeException("삭제할 사용자를 찾을 수 없습니다"));
			
			//권한을 가진 유저의 수정 요청인지 확인
			if(currentUser.getUsersId()!=targetUser.getUsersId()) throw new AccessDeniedException("자기 자신만 삭제 가능합니다");
			
			usersRepository.deleteById(usersId);
			return UsersDto.toDto(targetUser);
		}


		public UsersDto editUser(UsersDto usersDto, Long usersId, String authEmail) {
			UsersEntity currentUser = usersRepository.findByEmail(authEmail);
			UsersEntity targetUser = usersRepository.findById(usersId).orElseThrow(()->new RuntimeException("수정할 사용자를 찾을 수 없습니다"));
			
			//권한을 가진 유저의 수정 요청인지 확인
			if(currentUser.getUsersId()!=targetUser.getUsersId()) throw new AccessDeniedException("자기 자신만 수정 가능합니다");
			
			
			UsersEntity usersEntity =usersRepository.findById(usersId).orElseThrow(()->new IllegalArgumentException(usersId+"가 존재하지 않습니다"));
			BiosEntity biosEntity = biosRepository.findByUser(usersEntity);
			AuthEntity authEntity = authRepository.findByUser(usersEntity);
			usersEntity.setUsersName(usersDto.getUsersName());
			usersEntity.setProfileImg(usersDto.getProfileImg()!=null?usersDto.getProfileImg():null);
			usersEntity.setUpdateDate();
			
			
			//아래는 연관관계 업데이트
			
			//biosDto 업데이트
			if(usersDto.getBiosDto()!=null) {
				biosEntity.setAge(usersDto.getBiosDto().getAge());
				biosEntity.setGender(usersDto.getBiosDto().isGender());
				biosEntity.setHeight(usersDto.getBiosDto().getHeight());
				biosEntity.setWeight(usersDto.getBiosDto().getWeight());
				biosEntity.setUser(usersEntity);
			}
			else {
				usersEntity.setBios(null);
			}
			
			
			//authDto 업데이트
			authEntity.setPassword(usersDto.getAuthDto().getPassword());
			
			return UsersDto.toDto(usersEntity);
		}

		
		public UsersDto login(LoginRequest request) {
			UsersEntity usersEntity = usersRepository.findByEmailAndPassword(request.getEmail(),request.getPassword()).orElseThrow(()-> new IllegalArgumentException("올바르지 않은 아이디/비밀번호입니다"));
			
			return UsersDto.toDto(usersEntity);
			
		}
		
		
		
}
