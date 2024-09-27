package com.green.myAppApi.domain.entity;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitiallizer {
	
	@Bean
    ModelMapper modelMapper() {
        // ModelMapper 객체 생성
        ModelMapper modelMapper = new ModelMapper();
        
        // ModelMapper의 설정을 구성
        modelMapper.getConfiguration()
            // 필드 이름이 일치하면 자동으로 매핑되도록 설정 (기본은 false)
            .setFieldMatchingEnabled(true)
            
            // 필드 접근 수준을 설정 (기본은 PUBLIC, 여기서는 PRIVATE도 포함)
            .setFieldAccessLevel(AccessLevel.PRIVATE)            
            /*
             * 매칭 전략을 LOOSE로 설정할 수 있음. LOOSE 전략은 속성 이름이 부분적으로 일치해도
             * 매핑을 시도하는 방식으로, 좀 더 유연한 매핑을 제공.
             * 아래 코드는 주석 처리되어 있지만, 필요시 사용할 수 있음.
             */
            //.setMatchingStrategy(MatchingStrategies.LOOSE)
            ;
        
        // 설정이 완료된 ModelMapper 객체를 반환
        return modelMapper;
    }
	
	@Bean
	CommandLineRunner initData1(PostEntityRepository repository) {
		
		return args->{
			if(repository.count()==0) {
				for(int i=1; i<=10; i++) {
					repository.save(PostEntity.builder()
							.title("제목"+i)
							.content("내용"+i)
							.writer("테스트")
							.build());
				}
			}
		};
	}
	
	@Bean
	CommandLineRunner initData2(MemberEntityRepository repository,PasswordEncoder encoder) {
		
		return args->{
			if(repository.count()==0) {
				for(int i=1; i<=9; i++) {
					
					MemberEntity member=MemberEntity.builder()
							.email("user"+i+"@test.com")
							.pw(encoder.encode("1234"))
							.nickname("user"+i)
							.build();
					member.addRole(MemberRole.USER);
					
					if(i>=7) {
						member.addRole(MemberRole.MANAGER);
					}
					
					if(i==9) {
						member.addRole(MemberRole.ADMIN);
					}
					
					repository.save(member);
				}
			}
		};
	}

}
