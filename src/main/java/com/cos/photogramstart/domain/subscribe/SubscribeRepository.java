package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer>{
	
	//native query 작성, 쿼리 내부의 :은 함수의 변수를 바인드해서 넣겠다는 문법
	// 해당 객체를 받는 쿼리밖에 사용못함 (Subscribe)
	// 새로운 형식의 dto로 받으려면 SubscribeService 참고 (qlrm)
	
	@Modifying //데이터베이스에 변경을 주는 네이티브 쿼리는 이 어노테이션 필요 (INSERT, UPDATE, DELETE)
	@Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
	void mSubscribe(int fromUserId, int toUserId);
	
	//return 타입이 int이면 : 성공하면 변경된 행 개수, 실패하면 -1 반환됨
	// -> 변경된 행의 개수만큼 숫자를 return함, ex) 행 10개 바꾸면 10 return, 0 return 하면 변경된게 없다는 뜻
	// void 해도 상관없음
	
	@Modifying
	@Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
	void mUnSubscribe(int fromUserId, int toUserId);
	
	
	@Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalId AND toUserId = :pageUserId", nativeQuery = true)
	int mSubscribeState(int principalId, int pageUserId);
	
	@Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :pageUserId", nativeQuery = true)
	int mSubscribeCount(int pageUserId);
}
