package com.cos.photogramstart.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//공통 응답 DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CMRespDto<T> { //generic 사용, 다른 데이터를 리턴해야 할 수도 있기 때문(다른 객체나 String..)
	private int code; // 1(성공) , -1(실패)
	private String message;
	private T data;
	
}
