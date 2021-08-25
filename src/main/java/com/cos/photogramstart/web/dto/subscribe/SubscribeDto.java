package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscribeDto {
	private int id; // 대상 아이디
	private String username; //유저 이름
	private String profileImageUrl; // 대상 유저 사진
	private Integer subscribeState; // 구독을 한 상태인지
	private Integer equalUserState; // 로그인한 사람과 같은 사람인지
}
