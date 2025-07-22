package com.project.mog.controller;

import com.project.mog.dto.PostDto;
import com.project.mog.security.UsersDetails;
import com.project.mog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController                  
@RequestMapping("/api/v1/posts") 
@RequiredArgsConstructor         
public class PostController {
    private final PostService postService;

    // 1) 게시글 등록 API
    @PostMapping
    public ResponseEntity<PostDto> create(@RequestBody PostDto dto  
    									) {
    	//@AuthenticationPrincipal UsersDetails userDetails
    	
    	 // 🔑 로그인한 유저의 ID 가져오기
    	//long userId = userDetails.getUsersEntity().getUsersId();

        // 👉 dto에 userId 주입 (엔티티 만들 때 사용됨)
    	 dto.setUserId(1L); // 로그인 없이 테스트용 고정 userId
    	
    	PostDto created = postService.create(dto);
        return ResponseEntity.ok(created);
    }

    // 2) 게시글 목록 조회 API
    @GetMapping
    public ResponseEntity<List<PostDto>> list() {
        return ResponseEntity.ok(postService.listAll());
    }
    // 3) 게시글 수정 API
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostDto dto) {
        PostDto updated = postService.update(id, dto);
        return ResponseEntity.ok(updated);
    }
    // 4) 게시글 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        PostDto post = postService.getById(id);
        return ResponseEntity.ok(post);
    }
    // 5) 게시글 삭제 API
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
    
    
}
