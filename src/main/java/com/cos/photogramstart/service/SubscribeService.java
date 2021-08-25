package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em; // 모든 Repository는 EntityManager를 구현해서 만들어져있는 구현체
	
	
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


	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalId, int pageUserId){
		StringBuffer sb = new StringBuffer();
		//쿼리 준비
		sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
		sb.append("if ((SELECT 1 FROM subscribe WHERE fromUserId=? AND toUserId=u.id), 1, 0) subscribeState, "); // ?: principal id
		sb.append("if ((?=u.id), 1, 0) equalUserState "); // ? : 로그인한 아이디, principal id
		sb.append("FROM user u INNER JOIN subscribe s ");
		sb.append("ON u.id = s.toUserId ");
		sb.append("WHERE s.fromUserId=?"); //세미콜론 첨부 x, ? : pageUserId 자리
		
		
		//쿼리 완성
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId);
		
		//쿼리 실행 (qlrm 라이브러리 필요 : DTO에 DB결과를 맵핑하기 위해서)
		//qlrm : 데이터베이스에서 result된 결과를 자바 클래스에 맵핑해주는 라이브러리
		// pom.xml에 의존성 추가
		JpaResultMapper result = new JpaResultMapper();
		List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);
		return subscribeDtos;
	}
	
}
