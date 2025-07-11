package com.project.mog.service.comment;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class CommentDto {
	
	public static class Request{
		private String content;
	}//
	public static class Response {
        private Long id;
        private String content;
        private String username; //
        private LocalDateTime createdAt;
	}////

}
