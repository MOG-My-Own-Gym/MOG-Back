package com.project.mog.repository.comment;

import java.time.LocalDateTime;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.project.mog.repository.users.UsersEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
public class CommentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
	private long id;
	
	@Column(name = "comment_content",
			nullable = false,length = 100)
	private String content;
	
	//post OneToMany 일단작성후 지민님이랑 소통후 설정
	/*
	@OneToMany
	@JoinColumn(name = "postId",
				nullable = false)
	private postEntity post;*/

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UsersEntity user;
	
	@CreationTimestamp 
	@Column(name = "comment_reg_date", updatable = false) 
	private LocalDateTime createdAt;
	
}
