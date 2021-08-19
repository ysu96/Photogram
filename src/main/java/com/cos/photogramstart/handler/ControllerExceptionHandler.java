package com.cos.photogramstart.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController // 데이터 응답을 위해
@ControllerAdvice  //모든 exception을 낚아채 처리함
public class ControllerExceptionHandler {
//	@ExceptionHandler(CustomValidationException.class) //해당 exception이 발생하면 이 함수가 가로챔
//	public  CMRespDto<?> validationException(CustomValidationException e) {
//		return new CMRespDto(-1, e.getMessage(), e.getErrorMap());
//	}
	
	// CMRespDto, Script 비교
	// 1. 클라이언트에게 응답할 때는 Script가 좋음.
	// 2. Ajax통신 - CMRespDto (개발자가 js코드로 서버쪽으로 던져서 응답받는 것)
	// 3. Android 통신 - CMRespDto (응답을 안드로이드 앱에서 개발자가 해주는 것)
	// -> 응답 받는 쪽이 클라이언트면 Script, 개발자면 CMRespDto 가 좋음 
	@ExceptionHandler(CustomValidationException.class) //해당 exception이 발생하면 이 함수가 가로챔
	public  String validationException(CustomValidationException e) {
		return Script.back(e.getErrorMap().toString()); //스크립트 리턴
	}

	
	@ExceptionHandler(CustomValidationApiException.class) //Ajax통신 - 데이터 응답
	public  ResponseEntity<?> validationApiException(CustomValidationApiException e) { 
		//update.js에서 fail 부분을 실행하기 위해 http 상태코드를 같이 넘겨줌
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST ); 
	}

	@ExceptionHandler(CustomApiException.class) //Ajax통신 - 데이터 응답
	public  ResponseEntity<?> apiException(CustomApiException e) { 
		//update.js에서 fail 부분을 실행하기 위해 http 상태코드를 같이 넘겨줌
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST ); 
	}
	
}
