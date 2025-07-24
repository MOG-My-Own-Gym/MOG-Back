package com.project.mog.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data               
@NoArgsConstructor  
@AllArgsConstructor 
@Builder            
public class PostDto {
    private Long postId;        // 글 번호
    private Long userId;        // 작성자 ID
    private String postTitle;   // 제목
    private String postContent; // 내용
    private String postImage;   // 이미지 URL
    private LocalDateTime postRegDate; // 등록 시각
    private LocalDateTime postUpDate;  // 수정 시각


    public com.project.mog.entity.PostEntity toEntity() {
    return com.project.mog.entity.PostEntity.builder()
            .userId(this.userId)
            .postTitle(this.postTitle)
            .postContent(this.postContent)
            .postImage(this.postImage)
            .postRegDate(LocalDateTime.now())  // 등록 시각 자동 세팅
            .postUpDate(null)                  // 수정 시각은 처음엔 null
            .build();
    }
}
