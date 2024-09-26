package com.green.myAppApi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.green.myAppApi.domain.dto.post.PostDetailDTO;
import com.green.myAppApi.domain.dto.post.PostSaveDTO;

public interface PostService {

	Page<?> listProcess(int page);

	PostDetailDTO detailProcess(Long pno);

	void saveProcess(PostSaveDTO dto);

}
