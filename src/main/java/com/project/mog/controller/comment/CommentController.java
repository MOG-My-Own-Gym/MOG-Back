package com.project.mog.controller.comment;

import com.project.mog.security.jwt.JwtUtil;
import com.project.mog.service.comment.CommentService;
import com.project.mog.service.comment.dto.CommentResponseDto;
import com.project.mog.service.comment.dto.CommentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {
	private final JwtUtil jwtUtil;
    private final CommentService commentService;
    
    @GetMapping("/comments/list")
    public ResponseEntity<List<CommentResponseDto>> getAllComments(@RequestHeader("Authorization") String authHeader){
    	String token = authHeader.replace("Bearer ", "");
		String authEmail = jwtUtil.extractUserEmail(token);
    	return ResponseEntity.ok(commentService.getAllComments(authEmail));
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> addComment(@RequestHeader("Authorization") String authHeader, @PathVariable Long postId, @RequestBody CommentSaveRequestDto requestDto) {
    	String token = authHeader.replace("Bearer ", "");
		String authEmail = jwtUtil.extractUserEmail(token);
        CommentResponseDto newComment = commentService.createComment(postId, authEmail, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<Void> removeComment(@RequestHeader("Authorization") String authHeader,@PathVariable Long postId, @PathVariable Long commentId) {
    	String token = authHeader.replace("Bearer ", "");
		String authEmail = jwtUtil.extractUserEmail(token); 
        commentService.deleteComment(commentId, authEmail);
        return ResponseEntity.noContent().build();
    }
}