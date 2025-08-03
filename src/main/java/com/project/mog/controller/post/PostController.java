package com.project.mog.controller.post;

import com.project.mog.security.jwt.JwtUtil;
import com.project.mog.service.comment.dto.CommentResponseDto;
import com.project.mog.service.post.PostDto;
import com.project.mog.service.post.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
	private final JwtUtil jwtUtil;
    private final PostService postService;

    // 1) 게시글 등록
    @PostMapping
    public ResponseEntity<PostDto> create(@RequestHeader("Authorization") String authHeader,@RequestBody PostDto dto) {
    	String token = authHeader.replace("Bearer ", "");
		String authEmail = jwtUtil.extractUserEmail(token);
    	PostDto created = postService.create(authEmail,dto);
        return ResponseEntity.ok(created);
    }

    // 2) 회원 게시글 목록 조회 
    @GetMapping
    public ResponseEntity<List<PostDto>> list(@RequestHeader("Authorization") String authHeader) {
    	String token = authHeader.replace("Bearer ", "");
		String authEmail = jwtUtil.extractUserEmail(token);
        return ResponseEntity.ok(postService.listAll(authEmail));
    }

    // 3) 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostDto dto) {
        PostDto updated = postService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // 4) 회원 게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        PostDto post = postService.getById(id);
        return ResponseEntity.ok(post);
    }
    
    //5) 게시글 전체 조회 (모든 게시글)
    @GetMapping("/list")
    public ResponseEntity<List<PostDto>> getTotalList() {
        return ResponseEntity.ok(postService.totalListAll());
    }


    // 6) 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<PostDto> deletePost(@PathVariable Long id) {
        PostDto post = postService.delete(id);
        return ResponseEntity.ok(post); 
    }

    // 7) 이미지 업로드 API
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String uploadDir = "C:/uploads";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File saveFile = new File(uploadDir, fileName);
            file.transferTo(saveFile);

            String imageUrl = "/images/" + fileName;
            System.out.println("✔ 이미지 저장 성공: " + saveFile.getAbsolutePath());
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            System.out.println("❌ 이미지 저장 실패: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("이미지 업로드 실패");
        }
    }
    
}