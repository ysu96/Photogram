package com.cos.photogramstart.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final SubscribeRepository subscribeRepository;
	
	@Transactional(readOnly = true) //select하는 거니깐 readOnly
	public UserProfileDto 회원프로필(int pageUserId, int principalId) {
		UserProfileDto dto = new UserProfileDto(); 
				
		//SELECT * FROM image WHERE userId = :userId;
		User userEntity = userRepository.findById(pageUserId).orElseThrow(()->{
			throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
		});
		
		dto.setUser(userEntity);
		dto.setPageOwnerState(pageUserId == principalId);
		dto.setImageCount(userEntity.getImages().size());
		
		int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
		int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);
		
		dto.setSubscribeCount(subscribeCount);
		dto.setSubscribeState(subscribeState==1);
		return dto;
	}
	
	@Transactional
	public User 회원수정(int id, User user) {
		//1. 영속화
		
		//1) 무조건 찾았다, 걱정마 - get()   2) 못 찾았어 익셉션 발동시킬께 - orElseThrow() 
		User userEntity = userRepository.findById(id).orElseThrow( () -> { // 인자가 잘못된 경우
				return new CustomValidationApiException("찾을 수 없는 id입니다.");
		}); //리턴값이 optional이라  / 회원이 있는지 없는지 찾아
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
