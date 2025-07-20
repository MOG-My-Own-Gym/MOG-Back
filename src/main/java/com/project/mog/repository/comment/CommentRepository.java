package com.project.mog.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CommentRepository extends JpaRepository<CommentEntity, Long> { 
    
	@Query(nativeQuery = true,value ="SELECT * FROM COMMENTS WHERE POSTID=?1")
    List<CommentEntity> findByPostIdOrderByCreatedAtDesc(Long postId); 
}