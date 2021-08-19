package com.cos.photogramstart.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	private final SubscribeRepository subscribeRepository;
	
	@Transactional //데이터베이스에 영향을 주니깐
	public void 구독하기(int fromUserId, int toUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		}catch (Exception e) {
			throw new CustomApiException("이미 구독을 하였습니다.");
		}
		
	
	}
	
	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
		// 삭제는 오류 날 일이 없음, 삭제 실패해도 오류 안남
	}

}
