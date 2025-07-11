package com.project.mog.repository.comment;

import com.project.mog.repository.users.UsersEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "likes")
@Getter
@Setter
public class LikesEntity {
	
	@Id
	@GeneratedValue
	@Column(name = "likes_id")
	private long id;
	
	//post OneToMany 일단작성후 지민님이랑 소통후 설정
		/*
	@OneToMany
	@JoinColumn(name = "postId",
				nullable = false)
	private postEntity post;*/
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",nullable = false)	
	private UsersEntity user;
	
	private int likesStatus=0; //사용자당 한번만 클릭할수있도록
	
	
}
