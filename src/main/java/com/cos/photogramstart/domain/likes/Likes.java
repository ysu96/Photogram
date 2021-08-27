package com.cos.photogramstart.domain.likes;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// like 이 DB의 키워드여서 사용하지 못하기 때문에 likes로 정함
@Builder 
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(//복합 중복을 막기 위해 유니크 제약조건 생성
		uniqueConstraints = {
				@UniqueConstraint(
						name="likes_uk",//제약조건 이름
						columnNames = {"imageId", "userId"}// 실제 데이터베이스의 컬럼명으로 설정해야함
				)
		}
)
public class Likes {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id; 
	
//	image : like = 1:N
//	user : like = 1:N
	
	// ManyToOne : default EAGER / OneToMany : default LAZY
	@JoinColumn(name="imageId")
	@ManyToOne
	private Image image;
	
	@JsonIgnoreProperties({"images"}) // 무한참조 방지 image 불러올때 likes도 불러오고 likes불러오면 user도 불러오는데 user안에 images가 또있어서
	@JoinColumn(name="userId")
	@ManyToOne
	private User user;
	
	private LocalDateTime createDate;
	
	@PrePersist // db에 insert 되기 직전에 실행
	public void createDate() { 
		this.createDate = LocalDateTime.now(); // 
	}

}
