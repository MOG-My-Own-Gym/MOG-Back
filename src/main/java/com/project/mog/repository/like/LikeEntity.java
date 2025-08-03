package com.project.mog.repository.like;

import com.project.mog.repository.auth.AuthEntity;
import com.project.mog.repository.post.PostEntity;
import com.project.mog.repository.users.UsersEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes")
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likesid")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usersid", referencedColumnName="usersId", nullable = false)
    private UsersEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postid", nullable = false)
    private PostEntity post;

    public LikeEntity(UsersEntity user, PostEntity post) {
        this.user = user;
        this.post = post;
    }
}