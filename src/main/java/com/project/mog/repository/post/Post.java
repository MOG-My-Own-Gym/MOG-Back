package com.project.mog.repository.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "POSTS")
public class Post {

    @Id
    @Column(name = "POSTID")
    private Long postId;

    @Column(name = "POSTTITLE")
    private String postTitle;

    @Column(name = "POSTCONTENT")
    private String postContent;

    @Column(name = "POSTIMAGE")
    private String postImage;

    @Column(name = "POSTREGDATE")
    private LocalDateTime postRegDate;

    @Column(name = "POSTUPDATE")
    private LocalDateTime postUpdate;
}