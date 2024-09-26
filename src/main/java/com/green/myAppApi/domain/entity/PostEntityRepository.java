package com.green.myAppApi.domain.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostEntityRepository extends JpaRepository<PostEntity, Long>{

	List<PostEntity> findAllByOrderByPnoDesc();

	Page<PostEntity> findAllByOrderByPnoDesc(Pageable pageable);

}
