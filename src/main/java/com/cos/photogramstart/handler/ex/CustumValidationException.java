package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustumValidationException extends RuntimeException{
	//객체를 구분할 때 사용 , 사용자한텐 중요x 
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errorMap;
	
	public CustumValidationException(String message, Map<String, String> errorMap) {
		super(message);	
		this.errorMap = errorMap;
	}

	public Map<String, String> getErrorMap() {
		return errorMap;
	}
	
	
}
