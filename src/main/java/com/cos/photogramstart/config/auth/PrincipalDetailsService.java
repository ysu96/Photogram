package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service //IoC,  스프링컨테이너에 원래 있는 UserDetailsService를 덮어 씌워 대체함
public class PrincipalDetailsService implements UserDetailsService{
	private final UserRepository userRepository;
	
	//로그인을 실행하면 이 함수가 실행됨
	
	//리턴이 잘되면 자동으로 UserDetails타입을 세션으로 만든다. 
	//(SecurityContextHolder -> Authentication객체 안에 (PrincipalDetails) 집어넣고 SecurityContextHolder에 넣음 )
	//@AuthenticationPrincipal 어노테이션을 사용하면 Authentication 객체에 바로 접근할 수 있음 
	
	//패스워드는 알아서 체킹하니깐 신경쓸 필요 없음
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User userEntity = userRepository.findByUsername(username);
		if(userEntity == null) {
			
			return null;
		}
		else {
	
			return new PrincipalDetails(userEntity);
		}
		//비밀번호는 시큐리티가 알아서 비교해줌
		
	}

}
