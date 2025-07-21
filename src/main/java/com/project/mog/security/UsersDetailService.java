package com.project.mog.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.mog.repository.users.UsersEntity;
import com.project.mog.repository.users.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersDetailService implements UserDetailsService{

	private final UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
			UsersEntity users = usersRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("유효하지 않은 사용자입니다"));
			if(users==null) throw new UsernameNotFoundException("계정을 찾을 수 없습니다");
			return new UsersDetails(users);
		
	}
	
	
	
}
