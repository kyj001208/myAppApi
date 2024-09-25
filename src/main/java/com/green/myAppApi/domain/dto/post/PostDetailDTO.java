package com.green.myAppApi.domain.dto.post;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class PostDetailDTO {
	private Long pno;
	private String title;
	private String content;
	private String writer;
	private LocalDateTime updatedAt;
}
