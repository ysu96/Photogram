package com.cos.photogramstart.handler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

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
		
		return proceedingJoinPoint.proceed(); // 해당 함수로 다시 돌아가
	}
	
	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))") 
	public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		return proceedingJoinPoint.proceed();
	}
}
