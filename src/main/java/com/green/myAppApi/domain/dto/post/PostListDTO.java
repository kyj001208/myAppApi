package com.green.myAppApi.domain.dto.post;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class PostListDTO {
	private Long pno;
	private String title;
	private String writer;
	private LocalDateTime updatedAt;
}
