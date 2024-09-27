package com.green.myAppApi.domain.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberEntityRepository extends JpaRepository<MemberEntity, String>{
	
	//memberRoleList 을 즉시 로딩한다.
	@EntityGraph(attributePaths = {"memberRoleList"})
	Optional<MemberEntity> findByEmail(String email);
	
	
	Optional<MemberEntity> readByEmail(String email);


}
