package com.green.myAppApi.service;

import java.util.List;

import com.green.myAppApi.domain.dto.post.PostDetailDTO;
import com.green.myAppApi.domain.dto.post.PostSaveDTO;

public interface PostService {

	List<?> listProcess();

	PostDetailDTO detailProcess(Long pno);

	void saveProcess(PostSaveDTO dto);

}
