package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	private String name; //필수
	private String password; //필수
	private String website;
	private String bio;
	private String phone;
	private String gender;

	
	//필수 값들이 있어서 위험, 코드 수정 필요, validation 체크 해야댐
	public User toEntity() {
		return User.builder()
				.name(name)
				.password(password)
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
