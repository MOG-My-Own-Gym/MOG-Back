package com.project.mog.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.mog.security.UsersDetailService;
import com.project.mog.security.UsersDetails;
import com.project.mog.security.jwt.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UsersDetailService usersDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain)
									throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		System.out.println("JwtAuthenticationFilter doFilterInternal 실행됨: " + request.getRequestURI());
		if(header!=null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			System.out.println("토큰: " + token);
			String email = jwtUtil.extractUserEmail(token);
			
			if(email!=null&&SecurityContextHolder.getContext().getAuthentication()==null) {
				try {
					UserDetails usersDetails = usersDetailService.loadUserByUsername(email);
				
				
					if(jwtUtil.isTokenValid(token,usersDetails)) {
						UsernamePasswordAuthenticationToken authentication =
								new UsernamePasswordAuthenticationToken(email, null, usersDetails.getAuthorities());
						System.out.println("인증 객체 설정됨: " + authentication);
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
				catch(UsernameNotFoundException ex) {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			        response.setContentType("text/plain;charset=UTF-8");
				}
			}
			
		}
		filterChain.doFilter(request, response);
		
	}

	
}
