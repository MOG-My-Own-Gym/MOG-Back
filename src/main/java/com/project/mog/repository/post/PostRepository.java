package com.project.mog.repository.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.mog.service.post.PostDto;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

	@Query(nativeQuery=true, value="SELECT * FROM POSTS WHERE USERSID=?1 ORDER BY POSTREGDATE DESC")
	List<PostEntity> findAllOrderByRegDate(Long usersId);
    
}
