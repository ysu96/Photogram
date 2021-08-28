package com.cos.photogramstart.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;

//advice : 공통기능
@Aspect //aop 할 수 있는 핸들러가 됨
@Component // restController, Service 등 모든 것들이 Component를 상속해서 만들어져 있음
public class ValidationAdvice {
	
	//@Before : 어떤 특정 함수가 실행되기 직전에 실행
	//@After : 어떤 특정 함수가 실행된 후에 실행
	//@Around : 특정 함수 실행 전에 실행하고 끝날 때까지 관여
	
	//*1: 모든 접근 지정자(public ..), *2:모든 컨트롤러로 끝나는 애들, *3: 모든 메소드, (..): 모든 파라미터 
	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))") 
	public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		//proceedingJoinPoint : 실행되는 함수의 모든 정보에 접근할 수 있는 파라미터
		Object[] args = proceedingJoinPoint.getArgs(); // 함수의 매개변수

		for(Object arg: args) {
			if(arg instanceof BindingResult) {

				BindingResult bindingResult = (BindingResult) arg;
				
				//@Valid에서 오류가 발생하면 BindingResult에 오류를 다 모아줌 -> getFieldErrors 콜렉션에 다 모아줌
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					
					for(FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
					//유효성 검사 실패 -> BindingResult -> errorMap -> throw CustomValidationException -> ControllerExceptionHandler -> validationException함수 -> CMRespDto 리턴
				}
				
			}
		}
		return proceedingJoinPoint.proceed(); // 해당 함수로 다시 돌아가
	}
	
	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))") 
	public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
		Object[] args = proceedingJoinPoint.getArgs();
		System.out.println("=========== 그냥 ===========");
		for(Object arg: args) {
			if(arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;
				
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					
					for(FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					throw new CustomValidationException("유효성 검사 실패함", errorMap);
					//유효성 검사 실패 -> BindingResult -> errorMap -> throw CustomValidationException -> ControllerExceptionHandler -> validationException함수 -> CMRespDto 리턴
				}
			}
		}
		
		return proceedingJoinPoint.proceed();
	}
}
