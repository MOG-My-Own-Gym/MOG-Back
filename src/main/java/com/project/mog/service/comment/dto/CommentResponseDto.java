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

    public CommentResponseDto(CommentEntity comment) { 
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.userName = comment.getUser().getUser().getUsersName();
        this.userId = comment.getUser().getAuthId();
        this.createdAt = comment.getCreatedAt();
    }
}