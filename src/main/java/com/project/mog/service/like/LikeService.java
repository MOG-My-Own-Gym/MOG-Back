package com.project.mog.service.like;

import com.project.mog.repository.auth.AuthEntity;
import com.project.mog.repository.auth.AuthRepository;
import com.project.mog.repository.like.LikeEntity; // import 변경
import com.project.mog.repository.like.LikeRepository;
import com.project.mog.repository.post.Post;
import com.project.mog.repository.post.PostRepository;
import com.project.mog.service.like.LikeResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final AuthRepository authRepository;

    @Transactional
    public LikeResponseDto toggleLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시물을 찾을 수 없습니다. ID: " + postId));
        AuthEntity user = authRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId));

        // 변수 타입을 Optional<LikeEntity>로 변경
        Optional<LikeEntity> existingLike = likeRepository.findByUserIdAndPostId(user.getAuthId(), postId);

        boolean isLiked;
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            isLiked = false;
        } else {
            // 생성자 호출을 new LikeEntity(..)로 변경
            likeRepository.save(new LikeEntity(user, post));
            isLiked = true;
        }

        long likeCount = likeRepository.countByPostId(postId);
        return new LikeResponseDto(postId, user.getAuthId(), isLiked, likeCount);
    }
}