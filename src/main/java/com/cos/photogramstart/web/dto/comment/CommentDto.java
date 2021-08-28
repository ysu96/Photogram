package com.cos.photogramstart.web.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

// NotNull = Null값 체크
// NotEmpty = 빈값, null 체크
// NotBlank = 빈 값, null, 빈 공백(스페이스) 체크
@Data
public class CommentDto {
	@NotBlank 
	private String content;
	
	@NotNull
	private Integer imageId;
	
	//toEntity가 필요없다.
}
