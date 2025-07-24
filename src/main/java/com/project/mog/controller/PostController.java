package com.project.mog.controller;

import com.project.mog.dto.PostDto;
import com.project.mog.service.PostService;
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
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")  // 프론트 연동 시 CORS 허용
public class PostController {

    private final PostService postService;

    // 1) 게시글 등록
    @PostMapping
    public ResponseEntity<PostDto> create(@RequestBody PostDto dto) {
        dto.setUserId(1L); // 🟡 임시 유저 ID
        PostDto created = postService.create(dto);
        return ResponseEntity.ok(created);
    }

    // 2) 게시글 목록 조회
    @GetMapping
    public ResponseEntity<List<PostDto>> list() {
        return ResponseEntity.ok(postService.listAll());
    }

    // 3) 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostDto dto) {
        PostDto updated = postService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // 4) 게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        PostDto post = postService.getById(id);
        return ResponseEntity.ok(post);
    }

    // 5) 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }

    // 6) 이미지 업로드 API
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