package com.project.mog.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.project.mog.repository.users.UsersEntity;
import com.project.mog.repository.users.UsersRepository;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class UserAuthorizationAspect {
	
	private final UsersRepository usersRepository;
	
	@Around("@annotation(com.project.mog.annotation.UserAuthorizationCheck)")
	public Object checkUserAuthorization(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Object[] args = joinPoint.getArgs();
		
		Long userId = null;
		String authEmail = null;
		
		for(Object arg : args) {
			if(arg instanceof Long) userId = (Long) arg;
			if(arg instanceof String && ((String) arg).contains("@")) authEmail = (String) arg;
		}
		System.out.println("ANNOTATION동작"+userId+" , "+ authEmail);
		if(userId == null|| authEmail==null) {
			throw new IllegalArgumentException("유저 정보가 충분하지 않습니다");
		}
		UsersEntity targetUser = usersRepository.findById(userId).orElseThrow(()->new RuntimeException("수정할 사용자를 찾을 수 없습니다"));
		UsersEntity currentUser = usersRepository.findByEmail(authEmail);
		
		if(currentUser.getUsersId()!=userId) {
			throw new AccessDeniedException("유저 권한이 없습니다");
		}
		
		return joinPoint.proceed();
	}

}
