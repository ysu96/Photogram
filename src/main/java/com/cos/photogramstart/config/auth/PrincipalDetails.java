package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User{
	//OAuth2 override
	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes; //{id:234235234, name:fsdf, email:sfd@sddf.com}
	}

	//OAuth2 override
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return (String) attributes.get("name");
	}

	
	private static final long serialVersionUID = 1L;
	
	private User user;
	private Map<String, Object> attributes; // oAuth2User 전용
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	//oAuth2User 전용
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}
	
	@Override //권한을 가져오는 함수, 권한이 한개가 아닐 수 있어서 콜렉션타입 리턴
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Collection<GrantedAuthority> collector = new ArrayList<>();
		
//		collector.add(new GrantedAuthority() {	
//			@Override
//			public String getAuthority() {
//				// TODO Auto-generated method stub
//				return user.getRole();
//			}
//		});
		
		//람다식 사용
		collector.add(()-> {return user.getRole();});
		
		
		return collector;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	
	
	@Override //이 계정이 만료가 되었니? , 아래 함수들 다 false이면 로그인 안됨
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override //이 계정이 잠겼는지?
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override //이 비밀번호..?
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
