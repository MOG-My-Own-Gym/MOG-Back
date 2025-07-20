package com.project.mog.service.like;

import lombok.Getter;

@Getter
public class LikeResponseDto {
    private final Long postId;
    private final Long userId;
    private final boolean isLiked;
    private final long likeCount;

    public LikeResponseDto(Long postId, Long userId, boolean isLiked, long likeCount) {
        this.postId = postId;
        this.userId = userId;
        this.isLiked = isLiked;
        this.likeCount = likeCount;
    }
}