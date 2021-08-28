package com.cos.photogramstart.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //final 걸린 모든애들 생성자를 만들어줌 , DI할 때 사용
@Controller // 1.IoC 2. file 리턴
public class AuthController {

	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final AuthService authService;
	
//	public AuthController(AuthService authService) { //의존성주입 생성자 주입, IoC해놔서 스프링컨테이너에서 찾아서 주입해줌
//		this.authService = authService;
//	}
	
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	//회원가입 버튼 클릭 -> /auth/signup -> /auth/signin
	@PostMapping("/auth/signup")
	//@ResponseBody // 이 어노테이션을 사용하면 일반 @Controller임에도 데이터를 리턴할 수 있음, 하지만 데이터를 리턴할 수도 있고 페이지를 리턴할 수도 있어서 지금은 사용 x
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { // key=value (x-www-form-urlencoded) , 객체 안의 변수들에 다 담겨서 전달됨 (변수 이름 같아야함!)
		//log.info(signupDto.toString()); //확인용
		
	
		//signupDto -> User로 만들기
		User user = signupDto.toEntity();
		//log.info(user.toString());
			
		User userEntity = authService.회원가입(user);
			
		return "auth/signin"; //회원가입 성공하면 로그인 페이지로
	}
	
	//CSRF 토큰 : 클라이언트가 서버에게 폼에 데이터를 넣고 서버에 전송할 때 
	//서버는 시큐리티가 감싸고 있어서 시큐리티가 먼저 검사를 함 (서버 도착 전) 
	//CSRF 토큰 검사 시행 (시큐리티가 회원가입페이지를 응답할 때(signup.jsp) CSRF토큰을 심음)
	//그러면 input tag에 임시 난수값이 생김
	//클라이언트가 값 입력하고 서버로 보내면 시큐리티가 자기가 심은 csrf토큰이 있나 검사함
	//정상적으로 페이지에 접근한 사용자인지 검사하기 위해 CSRF토큰 사용함
	
	//CSRF토큰 비활성화 (나중에 js로 요청할 때 귀찮아서) 
	//security config에 http.csrf().disable();
}
