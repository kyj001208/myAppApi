package com.green.myAppApi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.green.myAppApi.domain.dto.post.PostDetailDTO;
import com.green.myAppApi.domain.dto.post.PostListDTO;
import com.green.myAppApi.domain.dto.post.PostSaveDTO;
import com.green.myAppApi.domain.entity.PostEntity;
import com.green.myAppApi.domain.entity.PostEntityRepository;
import com.green.myAppApi.service.PostService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceProcess implements PostService{

	private final PostEntityRepository reopsitory;
	private final ModelMapper modelMapper;
	
	@Override
	public Page<?> listProcess(int page) {
		//int page=1;//현실에서 페이지 1
		//시작페이지 0부터, 음수는 불가
		int pageNumber= (page-1) < 0 ? 0 : (page-1); 
		int pageSize=3;//페이지당 개수
		Sort sort=Sort.by(Direction.DESC, "pno");
		Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
		//Page<?> result=reopsitory.findAll(pageable);
		Page<PostListDTO> result=reopsitory.findAll(pageable)
				.map(post->modelMapper.map(post, PostListDTO.class));
				
		return result;
	}

	@Override
	public PostDetailDTO detailProcess(Long pno) {
		
		return reopsitory.findById(pno)
				.map(post->modelMapper.map(post, PostDetailDTO.class))
				.orElseThrow();
	}

	@Override
	public void saveProcess(PostSaveDTO dto) {
		reopsitory.save(modelMapper.map(dto, PostEntity.class));
		
	}

}
