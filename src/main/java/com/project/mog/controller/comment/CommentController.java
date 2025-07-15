package com.project.mog.controller.comment;

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
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long postId, @RequestBody CommentSaveRequestDto requestDto) {
        Long currentUserId = 1L; 
        CommentResponseDto newComment = commentService.createComment(postId, currentUserId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> removeComment(@PathVariable Long postId, @PathVariable Long commentId) {
        Long currentUserId = 1L; 
        commentService.deleteComment(commentId, currentUserId);
        return ResponseEntity.noContent().build();
    }
}