package com.cos.photogramstart.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustumValidationException;
import com.cos.photogramstart.util.Script;

@RestController // 데이터 응답을 위해
@ControllerAdvice  //모든 exception을 낚아채 처리함
public class ControllerExceptionHandler {
//	@ExceptionHandler(CustumValidationException.class) //해당 exception이 발생하면 이 함수가 가로챔
//	public  CMRespDto<?> validationException(CustumValidationException e) {
//		return new CMRespDto(-1, e.getMessage(), e.getErrorMap());
//	}
	
	// CMRespDto, Script 비교
	// 1. 클라이언트에게 응답할 때는 Script가 좋음.
	// 2. Ajax통신 - CMRespDto (개발자가 js코드로 서버쪽으로 던져서 응답받는 것)
	// 3. Android 통신 - CMRespDto (응답을 안드로이드 앱에서 개발자가 해주는 것)
	// -> 응답 받는 쪽이 클라이언트면 Script, 개발자면 CMRespDto 가 좋음 
	@ExceptionHandler(CustumValidationException.class) //해당 exception이 발생하면 이 함수가 가로챔
	public  String validationException(CustumValidationException e) {
		return Script.back(e.getErrorMap().toString()); //스크립트 리턴
	}

}
