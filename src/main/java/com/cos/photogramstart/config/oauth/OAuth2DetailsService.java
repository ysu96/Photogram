package com.cos.photogramstart.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

// ex. 페이스북 아이디, 패스워드로 로그인했을 때 페이스북으로부터의 응답을 처리하는 서비스, 회원정보를 받으려고!
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService{
	
	@Override //페이스북 로그인 응답을 하면 userRequest에 정보를 담아서 이 함수를 실행함
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("oauth2 서비스!");
		OAuth2User oAuth2User = super.loadUser(userRequest);
		return super.loadUser(userRequest);
	}
}
