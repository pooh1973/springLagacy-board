package com.spring.board.persistent;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.board.domain.UserVO;

@Repository
public class UserDAOImpl implements UserDAO{

	@Inject// 스프링이 만든 sqlsession을 가져온다 : 의존성 주입
	private SqlSession session;
	static final String namespace = "com.spring.user";
	
	@Override
	public void insert(UserVO userVO) throws Exception {
		session.insert(namespace+".insert", userVO);
		
	}

	@Override
	public void delete(int userID) throws Exception {
		session.delete(namespace+".delete", userID);
	}
	
}
