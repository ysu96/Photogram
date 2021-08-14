package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;


//어노테이션 없어도 JpaRepository를 상속하면 IoC등록이 자동으로 됨
public interface UserRepository extends JpaRepository<User, Integer>{
	//JPA query method , findBy~쿼리를 자동으로 만들어줌
	User findByUsername(String username);
	
}
