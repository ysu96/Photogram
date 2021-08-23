package com.cos.photogramstart.handler.ex;

public class CustomException extends RuntimeException{
	//객체를 구분할 때 사용 , 사용자한텐 중요x 
	private static final long serialVersionUID = 1L;
	
	public CustomException(String message) {
		super(message);	
	}
}
