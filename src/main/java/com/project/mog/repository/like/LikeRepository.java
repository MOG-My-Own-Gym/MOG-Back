package com.project.mog.repository.like;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface LikeRepository extends JpaRepository<LikeEntity, Long> { 
    
    Optional<LikeEntity> findByUserIdAndPostId(Long userId, Long postId); 
    long countByPostId(Long postId);
}