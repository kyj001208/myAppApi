package com.green.myAppApi.service;

import java.util.List;

import com.green.myAppApi.domain.dto.post.PostDetailDTO;

public interface PostService {

	List<?> listProcess();

	PostDetailDTO detailProcess(Long pno);

}
