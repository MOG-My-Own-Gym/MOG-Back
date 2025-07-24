package com.project.mog.service.users;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.project.mog.annotation.UserAuthorizationCheck;
import com.project.mog.api.KakaoApiClient;
import com.project.mog.controller.auth.EmailFindRequest;
import com.project.mog.controller.login.LoginRequest;
import com.project.mog.controller.login.LoginResponse;
import com.project.mog.controller.login.SocialLoginRequest;
import com.project.mog.repository.auth.AuthEntity;
import com.project.mog.repository.auth.AuthRepository;
import com.project.mog.repository.bios.BiosEntity;
import com.project.mog.repository.bios.BiosRepository;
import com.project.mog.repository.users.UsersEntity;
import com.project.mog.repository.users.UsersRepository;
import com.project.mog.service.bios.BiosDto;

@Service
public class UsersService {

		private UsersRepository usersRepository;
		private BiosRepository biosRepository;
		private AuthRepository authRepository;
		private KakaoApiClient kakaoApiClient;
		
		
		
		public UsersService(UsersRepository usersRepository, BiosRepository biosRepository,AuthRepository authRepository, KakaoApiClient kakaoApiClient ) {
			this.usersRepository=usersRepository;
			this.biosRepository=biosRepository;
			this.authRepository=authRepository;
			this.kakaoApiClient=kakaoApiClient;
		}


		public List<UsersInfoDto> getAllUsers() {
			
			return usersRepository.findAll().stream().map(UsersInfoDto::toDto).collect(Collectors.toList());
		}


		public UsersDto createUser(UsersDto usersDto) {
			UsersEntity isDuplicated = usersRepository.findByEmail(usersDto.getEmail()).orElse(null);
			if(isDuplicated!=null) throw new IllegalArgumentException("중복된 아이디입니다");
			UsersEntity uEntity = usersRepository.save(usersDto.toEntity());
			return UsersDto.toDto(uEntity);
		}


		public UsersInfoDto getUser(Long usersId) {
			return usersRepository.findById(usersId).map(uEntity->UsersInfoDto.toDto(uEntity)).orElseThrow(()->new IllegalArgumentException("사용자를 찾을 수 없습니다"));
		}
		
		public UsersInfoDto getUserByEmail(String email) {
			return usersRepository.findByEmail(email).map(uEntity->UsersInfoDto.toDto(uEntity)).orElseThrow(()->new IllegalArgumentException("사용자를 찾을 수 없습니다"));
		}

		@UserAuthorizationCheck
		public UsersInfoDto deleteUser(Long usersId, String authEmail) {
			UsersEntity currentUser = usersRepository.findByEmail(authEmail).orElseThrow(()->new IllegalArgumentException("유효하지 않은 사용자입니다"));
			UsersEntity targetUser = usersRepository.findById(usersId).orElseThrow(()->new RuntimeException("삭제할 사용자를 찾을 수 없습니다"));
			
			//권한을 가진 유저의 수정 요청인지 확인
			if(currentUser.getUsersId()!=targetUser.getUsersId()) throw new AccessDeniedException("자기 자신만 삭제 가능합니다");
			
			usersRepository.deleteById(usersId);
			return UsersInfoDto.toDto(targetUser);
		}

		@UserAuthorizationCheck
		public UsersInfoDto editUser(UsersInfoDto usersInfoDto, Long usersId, String authEmail) {		
			UsersEntity usersEntity =usersRepository.findById(usersId).orElseThrow(()->new IllegalArgumentException(usersId+"가 존재하지 않습니다"));
			BiosEntity biosEntity = biosRepository.findByUser(usersEntity);
			
			return usersInfoDto.applyTo(usersEntity, biosEntity);
		}

		public UsersDto login(LoginRequest request) {
			UsersEntity usersEntity = usersRepository.findByEmailAndPassword(request.getEmail(),request.getPassword()).orElseThrow(()-> new IllegalArgumentException("올바르지 않은 아이디/비밀번호입니다"));
			
			return UsersDto.toDto(usersEntity);
			
		}


		public UsersDto socialLogin(SocialLoginRequest request) {
			System.out.println("social?");
			System.out.println(request.getSocialType().equalsIgnoreCase("kakao"));
			if(request.getSocialType().equalsIgnoreCase("kakao")) {
				System.out.println("on kakao?");
				KakaoUser kakaoUser = kakaoApiClient.getUserInfo(request.getAccessToken());
				UsersEntity usersEntity = usersRepository.findByEmail(String.format("user%s@kakao.com", kakaoUser.getId())).orElse(null);
				if(usersEntity==null) {
					AuthEntity newKakaoAuth = AuthEntity.builder().password(request.getAccessToken()).build();
					UsersEntity newKakaoUser = UsersEntity.builder()
													.usersName(kakaoUser.getProperties().getNickname())
													.email("user"+kakaoUser.getId()+"@kakao.com")
													.profileImg(kakaoUser.getProperties().getProfile_image())
													.nickName(kakaoUser.getProperties().getNickname())
													.bios(null)
													.auth(newKakaoAuth)
													.build();
					return createUser(UsersDto.toDto(newKakaoUser));
				}
				return UsersDto.toDto(usersEntity);
			}
			
			return null;
		}


		public UsersDto checkPassword(String authEmail, String password) {
			UsersEntity usersEntity = usersRepository.findByEmailAndPassword(authEmail, password).orElseThrow(()->new IllegalArgumentException("사용자를 찾을 수 없습니다"));
			return UsersDto.toDto(usersEntity);
			
		}


		
		public UsersDto editPassword(String authEmail, String originPassword, String newPassword) {
			UsersEntity usersEntity = usersRepository.findByEmailAndPassword(authEmail, originPassword).orElseThrow(()->new IllegalArgumentException("사용자를 찾을 수 없습니다"));
			AuthEntity authEntity = usersEntity.getAuth();
			authEntity.setPassword(newPassword);
			return UsersDto.toDto(usersEntity);
		}


		public UsersInfoDto getUserByRequest(EmailFindRequest emailFindRequest) {
			UsersEntity usersEntity = usersRepository.findByUsersNameAndPhoneNum(emailFindRequest.getUsersName(),emailFindRequest.getPhoneNum()).orElseThrow(()->new IllegalArgumentException("사용자를 찾을 수 없습니다"));
			return UsersInfoDto.toDto(usersEntity);
		}


		
		
		
		
}
