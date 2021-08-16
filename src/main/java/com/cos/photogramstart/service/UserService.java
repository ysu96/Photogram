package com.cos.photogramstart.service;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public User 회원수정(int id, User user) {
		//1. 영속화
		User userEntity = userRepository.findById(id).get(); //리턴값이 optional이라 .get() 사용 / 회원이 있는지 없는지 찾아
		// 영속성 컨텍스트 : 스프링부트 서버와 데이터베이스 사이에 이 객체가 영속화되어 들어옴
		// 영속화된 오브젝트는 변경하면 바로 데이터베이스에 적용이 된다!
		
		
		//2. 영속화된 오브젝트를 수정 - 더티체킹 (업데이트 완료)
		userEntity.setName(user.getName());
		
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword); //비밀번호 암호화
		userEntity.setPassword(encPassword);
		
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		
		return userEntity;
	} //더티 체킹이 일어나서 업데이트가 완료됨.

}
