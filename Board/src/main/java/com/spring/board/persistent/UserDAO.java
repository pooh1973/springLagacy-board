package com.spring.board.persistent;

import com.spring.board.domain.UserVO;

public interface UserDAO {
	void insert(UserVO userVO) throws Exception; // 인터페이스는 앞 선언에 public abstract(추상메소드)가 기본적으로 생략
	void delete(int userID) throws Exception;
}
