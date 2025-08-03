package com.project.mog.controller.like;

import com.project.mog.service.like.LikeService;
import com.project.mog.security.jwt.JwtUtil;
import com.project.mog.service.like.LikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/likes")
public class LikeController {
	private final JwtUtil jwtUtil;
    private final LikeService likeService;
    
    @GetMapping
    public ResponseEntity<LikeResponseDto> getLikes(@PathVariable Long postId){
    	LikeResponseDto response = likeService.getLikes(postId);
    	return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<LikeResponseDto> toggleLike(@RequestHeader("Authorization") String authHeader,@PathVariable Long postId) {
    	String token = authHeader.replace("Bearer ", "");
		String authEmail = jwtUtil.extractUserEmail(token);
        LikeResponseDto response = likeService.toggleLike(postId, authEmail);
        return ResponseEntity.ok(response);
    }
}