package com.project.mog.repository.comment;

import com.project.mog.repository.auth.AuthEntity;
import com.project.mog.repository.post.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comments")
public class CommentEntity { // 클래스 이름 변경

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName="authId", nullable = false)
    private AuthEntity user;

    @Column(name = "comment_content", nullable = false, length = 300)
    private String content;

    @CreatedDate
    @Column(name = "comment_up_date", updatable = false)
    private LocalDateTime createdAt;

    // 반환 타입을 CommentEntity로 변경
    public static CommentEntity of(Post post, AuthEntity user, String content) {
        CommentEntity comment = new CommentEntity();
        comment.post = post;
        comment.user = user;
        comment.content = content;
        return comment;
    }
}