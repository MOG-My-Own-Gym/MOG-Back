package com.project.mog.service.comment;

import com.project.mog.repository.auth.AuthEntity;
import com.project.mog.repository.auth.AuthRepository;
import com.project.mog.repository.comment.CommentEntity;
import com.project.mog.repository.comment.CommentRepository;
import com.project.mog.repository.post.Post;
import com.project.mog.repository.post.PostRepository;
import com.project.mog.service.comment.dto.CommentResponseDto;
import com.project.mog.service.comment.dto.CommentSaveRequestDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AuthRepository authRepository;

    @Transactional
    public CommentResponseDto createComment(Long postId, Long userId, CommentSaveRequestDto requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시물을 찾을 수 없습니다. ID: " + postId));
        AuthEntity user = authRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId));

        CommentEntity comment = CommentEntity.of(post, user, requestDto.getContent());
        CommentEntity savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment);
    }
    
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByPost(Long postId) {
        List<CommentEntity> comments = commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다. ID: " + commentId));

        if (comment.getUser().getAuthId() != userId) {
            throw new SecurityException("댓글을 삭제할 권한이 없습니다.");
        }
        commentRepository.delete(comment);
    }
}