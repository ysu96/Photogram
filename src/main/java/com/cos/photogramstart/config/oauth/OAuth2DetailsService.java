package com.cos.photogramstart.config.oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

// ex. 페이스북 아이디, 패스워드로 로그인했을 때 페이스북으로부터의 응답을 처리하는 서비스, 회원정보를 받으려고!
@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService{
	private final UserRepository userRepository;
	
	@Override //페이스북 로그인 응답을 하면 userRequest에 정보를 담아서 이 함수를 실행함
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		//System.out.println("oauth2 서비스!");
		OAuth2User oAuth2User = super.loadUser(userRequest);
		Map<String, Object> userInfo = oAuth2User.getAttributes();
		String name = (String) userInfo.get("name");
		String email = (String) userInfo.get("email");
		String username = "facebook_"+(String) userInfo.get("id");
		String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()); // 랜덤한 아무 숫자 
		
		User userEntity = userRepository.findByUsername(username);
		if(userEntity == null) { // 페이스북 최초 로그인
			User user = User.builder()
					.username(username)
					.password(password)
					.name(name)
					.email(email)
					.role("ROLE_USER")
					.build();
			
			return new PrincipalDetails(userRepository.save(user), userInfo);
			
		}else { // 이미 페이스북으로 회원가입이 되어있음
			return new PrincipalDetails(userEntity, userInfo);
		}
		
	}
}
