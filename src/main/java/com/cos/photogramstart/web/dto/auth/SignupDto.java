package com.cos.photogramstart.web.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data //getter , setter
public class SignupDto {
	//Dto : data transfer object , 통신을 위해 데이터를 담아두는 객체
	
	//https://bamdule.tistory.com/35 (@Valid 어노테이션 종류)
	@Size(min=2, max = 20)
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	@NotBlank
	private String email;
	@NotBlank
	private String name;

	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.build();
	}
}
