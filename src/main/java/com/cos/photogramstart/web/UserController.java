package com.cos.photogramstart.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;

@Controller
public class UserController {
	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id) {
		return "user/profile";
	}

	
	//(SecurityContextHolder -> Authentication객체 안에 (PrincipalDetails) 집어넣고 SecurityContextHolder에 넣음 )
		//@AuthenticationPrincipal 어노테이션을 사용하면 Authentication 객체에 바로 접근할 수 있음 
	//이렇게 하면 세션 접근 가능
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		//1.추천
		System.out.println("세션 정보 : "+ principalDetails.getUser());
		
		//2.극혐
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
		System.out.println("직접 찾은 세션 정보 : "+ mPrincipalDetails.getUser()); //복잡한 방법, 위랑 같음
		return "user/update";
	} 
}
