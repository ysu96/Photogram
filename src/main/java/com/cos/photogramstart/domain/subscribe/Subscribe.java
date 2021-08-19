package com.cos.photogramstart.domain.subscribe;

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

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder 
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(// fromUser와 toUser의 복합 중복을 막기 위해 유니크 제약조건 생성
		uniqueConstraints = {
				@UniqueConstraint(
						name="subscribe_uk",//제약조건 이름
						columnNames = {"fromUserId", "toUserId"}// 실제 데이터베이스의 컬럼명으로 설정해야함
				)
		}
)
public class Subscribe {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호증가 전략이 데이터베이스를 따라간다. (ex. mysql : auto increment)
	private int id; 
	
	@JoinColumn(name="fromUserId") // 이렇게 컬럼명 만들어! 니 맘대로 만들지 말고!!
	@ManyToOne // db table에 fromUser_id 컬럼이 생김 (ORM)
	private User fromUser; //구독하는 유저
	
	@JoinColumn(name="toUserId")
	@ManyToOne
	private User toUser; //구독 받는 유저
	
	private LocalDateTime createDate;
	
	@PrePersist // db에 insert 되기 직전에 실행
	public void createDate() { 
		this.createDate = LocalDateTime.now(); // 
	}
	
}
