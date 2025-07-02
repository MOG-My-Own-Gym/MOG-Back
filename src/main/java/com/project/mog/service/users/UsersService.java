package com.project.mog.service.users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.project.mog.repository.users.UsersEntity;
import com.project.mog.repository.users.UsersRepository;

@Service
public class UsersService {

		private UsersRepository usersRepository;
		
		@Autowired
		public UsersService(UsersRepository usersRepository) {
			this.usersRepository=usersRepository;
		}


		public List<UsersEntity> getAllUsers() {
			return usersRepository.findAll();
		}


		public UsersDto createUser(UsersDto usersDto) {
			UsersEntity uEntity = usersRepository.save(usersDto.toEntity());
			
			return UsersDto.toDto(uEntity);
		}


		public Optional<UsersDto> getUser(Long usersId) {
			
			return usersRepository.findById(usersId).map(uEntity->UsersDto.toDto(uEntity));
		}
		
		
}
