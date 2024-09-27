package com.green.myAppApi.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.green.myAppApi.domain.entity.MemberEntity;
import com.green.myAppApi.domain.entity.MemberRole;

public class MemberDTO extends User{
	
	private static final long serialVersionUID= 1L;
	private String email;
	//private String pw;
	private String nickname;
	private boolean social;
	private Set<MemberRole> memberRoles = new HashSet<>();

	public MemberDTO(MemberEntity member) {
		super(member.getEmail(), member.getPw(), member.getMemberRoles().stream()
				.map(role->new SimpleGrantedAuthority("ROLE_"+role)).toList());
		
		this.email=member.getEmail();
		//this.pw=member.getPw();
		this.nickname=member.getNickname();
		this.social=member.isSocial();
		this.memberRoles=member.getMemberRoles();
		
	}
	
	public Map<String, Object> getClaims(){
		Map<String, Object> map=new HashMap<>();
		map.put("email", email);
		//map.put("pw", pw);
		map.put("nickname", email);
		map.put("social", social);
		map.put("memberRoles", memberRoles);
		
		return map;
	}

}
