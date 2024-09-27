package com.green.myAppApi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.green.myAppApi.domain.dto.post.PostDetailDTO;
import com.green.myAppApi.domain.dto.post.PostSaveDTO;
import com.green.myAppApi.service.PostService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class PostController {
	private final PostService service;
	
	@GetMapping("/api/posts")//?page=1
	public Page<?>  list(@RequestParam(name = "page") int page) {
		
		return service.listProcess(page);
	}
	
	@GetMapping("/api/posts/{pno}")
	public PostDetailDTO detail(@PathVariable(name = "pno") Long pno) {
		return service.detailProcess(pno);
	}
	
	//@ResponseBody
	//@PreAuthorize("hasRole('admin')")
	@PreAuthorize("hasAnyRole('admin','user')")
	@PostMapping("/api/posts")
	public void  save(@RequestBody PostSaveDTO dto) {
		
		service.saveProcess(dto);
	}
	
	
}
