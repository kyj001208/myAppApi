package com.green.myAppApi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.green.myAppApi.domain.dto.post.PostDetailDTO;
import com.green.myAppApi.domain.dto.post.PostListDTO;
import com.green.myAppApi.domain.entity.PostEntityRepository;
import com.green.myAppApi.service.PostService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceProcess implements PostService{

	private final PostEntityRepository reopsitory;
	private final ModelMapper modelMapper;
	
	@Override
	public List<?> listProcess() {
		List<PostListDTO> result=reopsitory.findAll().stream()
			.map(post->modelMapper.map(post, PostListDTO.class))
			.toList();//불변리스트 16이상사용
			//.collect(Collectors.toList());//가변형 8이상에서
		
		//result.add(new PostListDTO()); 예외발생
		return result;
	}

	@Override
	public PostDetailDTO detailProcess(Long pno) {
		
		return reopsitory.findById(pno)
				.map(post->modelMapper.map(post, PostDetailDTO.class))
				.orElseThrow();
	}

}
