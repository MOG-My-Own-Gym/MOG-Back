package com.project.mog.controller.like;

import com.project.mog.service.like.LikeService;
import com.project.mog.service.like.LikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeResponseDto> toggleLike(@PathVariable Long postId) {
        Long currentUserId = 1L; 
        LikeResponseDto response = likeService.toggleLike(postId, currentUserId);
        return ResponseEntity.ok(response);
    }
}