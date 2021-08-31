package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	
	@Column(length = 100, unique = true) // 중복 아이디 방지!, 똑같은 아이디 쓰면 에러 발생시킴, auth2login 위해 길이 늘림
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
	
	
	// @OneToMany : mappedBy에 Image 클래스의 User 변수 이름 적기, 나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지마.
	// LAZY : 디폴트 값 , User를 Select할 때 해당 User id로 등록된 image들을 가져오지마 - 대신 getImages() 함수의 image들이 호출될 때 가져와!
	// EAGER : User를 Select할 때 해당 User id로 등록된 image들을 전부 Join해서 가져와!
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"user"}) //json파싱할때 얘 내부에 있는 user는 무시하고 파싱해줘, user getter가 호출되면 안돼/ 무한참조 막기용
	private List<Image> images; //양방향 맵핑
	
	
	private LocalDateTime createDate;
	
	@PrePersist // db에 insert 되기 직전에 실행
	public void createDate() { 
		this.createDate = LocalDateTime.now(); // 
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", email="
				+ email + ", website=" + website + ", bio=" + bio + ", phone=" + phone + ", gender=" + gender
				+ ", profileImageUrl=" + profileImageUrl + ", role=" + role + ", createDate="
				+ createDate + "]";
	}
	
	
	
}
