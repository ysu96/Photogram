package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DB에 저장하기 위한 모델
//JPA = Java persistence API (자바로 데이터를 영구적으로 저장(DB)할 수 있는 API를 제공)

@Builder //dto를 엔티티로 바꾸기 위해
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // db에 테이블을 생성
public class User {
	
	@Id //pk로 만들어줌
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호증가 전략이 데이터베이스를 따라간다. (ex. mysql : auto increment)
	private int id; //나중에 사용자가 많아지게 되면 Long을 써야함
	
	@Column(length = 20, unique = true) // 중복 아이디 방지!, 똑같은 아이디 쓰면 에러 발생시킴
	private String username;
	
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String email;
	
	private String website; //웹사이트
	private String bio; //자기소개
	
	private String phone;
	private String gender;
	
	private String profileImageUrl; // 사진
	private String role; // 권한
	
	private LocalDateTime createDate;
	
	@PrePersist // db에 insert 되기 직전에 실행
	public void createDate() { 
		this.createDate = LocalDateTime.now(); // 
	}
	
}
