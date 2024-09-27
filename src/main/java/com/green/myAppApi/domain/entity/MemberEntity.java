package com.green.myAppApi.domain.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "member")
@Entity
public class MemberEntity {
	
	 @Id
	  private String email;

	  private String pw;

	  private String nickname;

	  private boolean social;

	  @Enumerated(EnumType.STRING)
	  @CollectionTable(name = "memberRole")
	  @ElementCollection(fetch = FetchType.LAZY)
	  @Builder.Default
	  private Set<MemberRole> memberRoles = new HashSet<>();

	  public void addRole(MemberRole memberRole){

		  memberRoles.add(memberRole);
	  }

	  public void clearRole(){
	      memberRoles.clear();
	  }

	  public void changeNickname(String nickname) {
	    this.nickname = nickname;
	  }

	  public void changePw(String pw){
	    this.pw = pw;
	  }

	  public void changeSocial(boolean social) {
	    this.social = social;
	  }

}
