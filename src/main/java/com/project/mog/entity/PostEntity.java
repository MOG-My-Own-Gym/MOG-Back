package com.project.mog.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity                         
@Table(name = "posts")
@Getter @Setter                 
@NoArgsConstructor              
@AllArgsConstructor             
@Builder                        
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;         // 글 고유번호 (PK)

    private Long userId;         // 작성자 ID (외래키, 나중에 User와 연결)

    @Column(length = 100, nullable = false)
    private String postTitle;    // 글 제목

    @Column(length = 2000, nullable = false)
    private String postContent;  // 글 내용

    private String postImage;    // 이미지 URL (nullable)

    private LocalDateTime postRegDate; // 등록 시각
    private LocalDateTime postUpDate;  // 수정 시각
    
    
}
