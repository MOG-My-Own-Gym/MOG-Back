package com.project.mog.repository.comment;

import org.aspectj.apache.bcel.classfile.Module.Uses;

import com.project.mog.repository.users.UsersEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes",
	uniqueConstraints = {@UniqueConstraint(name="likes_uk",
	columnNames = {"user_id","post_id"})
	})
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
	
	 public LikesEntity(Uses users) {
	        this.user = user;
	        //this.post = post; // post 작성후 수정
	 }
	
}
