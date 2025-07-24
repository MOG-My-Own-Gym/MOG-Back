package com.project.mog.service;

import com.project.mog.dto.PostDto;
import com.project.mog.entity.PostEntity;
import com.project.mog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service                        
@RequiredArgsConstructor       
public class PostService {
    private final PostRepository postRepository;

    // 게시글 저장
    public PostDto create(PostDto dto) {
        // DTO → Entity
        PostEntity post = PostEntity.builder()
                .userId(dto.getUserId())
                .postTitle(dto.getPostTitle())
                .postContent(dto.getPostContent())
                .postImage(dto.getPostImage())
                .build();
        // 저장하고 영속화된 Entity 리턴
        PostEntity saved = postRepository.save(dto.toEntity());

        // Entity → DTO 변환 후 리턴
        return PostDto.builder()
                .postId(saved.getPostId())
                .userId(saved.getUserId())
                .postTitle(saved.getPostTitle())
                .postContent(saved.getPostContent())
                .postImage(saved.getPostImage())
                .postRegDate(saved.getPostRegDate())
                .postUpDate(saved.getPostUpDate())
                .build();
    }

    // 모든 게시글 조회
    public List<PostDto> listAll() {
        return postRepository.findAll().stream()
                .map(p -> PostDto.builder()
                        .postId(p.getPostId())
                        .userId(p.getUserId())
                        .postTitle(p.getPostTitle())
                        .postContent(p.getPostContent())
                        .postImage(p.getPostImage())
                        .postRegDate(p.getPostRegDate())
                        .postUpDate(p.getPostUpDate())
                        .build())
                .collect(Collectors.toList());
    }
    //게시들 한건 조회
    public PostDto getById(Long id) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다: id=" + id));

        return PostDto.builder()
                .postId(post.getPostId())
                .userId(post.getUserId())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .postImage(post.getPostImage())
                .postRegDate(post.getPostRegDate())
                .postUpDate(post.getPostUpDate())
                .build();
    }
    //게시글 수정
    public PostDto update(Long id, PostDto dto) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다: id=" + id));

        post.setPostTitle(dto.getPostTitle());
        post.setPostContent(dto.getPostContent());
        post.setPostImage(dto.getPostImage());
        post.setPostUpDate(java.time.LocalDateTime.now());

        PostEntity updated = postRepository.save(post);

        return PostDto.builder()
                .postId(updated.getPostId())
                .userId(updated.getUserId())
                .postTitle(updated.getPostTitle())
                .postContent(updated.getPostContent())
                .postImage(updated.getPostImage())
                .postRegDate(updated.getPostRegDate())
                .postUpDate(updated.getPostUpDate())
                .build();
    }
    //게시글 삭제
    public void delete(Long id) {
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 게시글이 없습니다: id=" + id);
        }
        postRepository.deleteById(id);
    }
    
}
