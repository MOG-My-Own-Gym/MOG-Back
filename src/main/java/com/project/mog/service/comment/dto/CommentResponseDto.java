package com.project.mog.service.comment.dto;

import com.project.mog.repository.comment.CommentEntity;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long commentId;
    private final String content;
    private final String userName;
    private final Long userId;
    private final LocalDateTime createdAt;
    private final Long postId;

    public CommentResponseDto(CommentEntity comment) { 
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.userName = comment.getUser().getUsersName();
        this.userId = comment.getUser().getUsersId();
        this.createdAt = comment.getCreatedAt();
        this.postId = comment.getPost().getPostId();
    }
}