package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration //IoC 
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	//비밀번호 암호화
	@Bean  // 이 클래스가 IoC에 등록될 때 @Bean 어노테이션을 읽어서 이 함수를 리턴해 IoC가 들고있음 / 우리는 쓰기만 하면 됨
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화됨.
		http.csrf().disable(); //csrf토큰 비활성화 (디폴트는 활성)
		http.authorizeRequests()
			.antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**").authenticated() //이런 주소로 들어오면 인증이 필요해
			.anyRequest().permitAll() //그게 아닌 모든 요청은 허용할게
			.and()
			.formLogin() //로그인 해야지
			.loginPage("/auth/signin") //로그인 페이지 / 인증이 필요하면 여기로 보내  , GET
			.loginProcessingUrl("/auth/signin") //POST, 누군가가 해당 주소로 로그인 요청을 하면 얘가 낚아 챔, 스프링 시큐리티에게 로그인을 위임함
			.defaultSuccessUrl("/"); //로그인이 정상적으로 되면 여기로 보내
	}
}
