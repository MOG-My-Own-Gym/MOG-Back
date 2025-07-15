package com.project.mog.repository.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface LikeRepository extends JpaRepository<LikeEntity, Long> { 
    @Query(nativeQuery = true,value ="SELECT * FROM LIKES WHERE USERSID=?1 AND POSTID=?2")
    Optional<LikeEntity> findByUserIdAndPostId(Long userId, Long postId); 
    @Query(nativeQuery=true, value="SELECT COUNT(*) FROM LIKES WHERE  POSTID=?1")
    long countByPostId(Long postId);
}