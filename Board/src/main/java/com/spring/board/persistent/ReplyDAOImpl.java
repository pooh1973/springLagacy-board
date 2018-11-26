package com.spring.board.persistent;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.board.domain.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO{

	@Inject// 스프링이 만든 sqlsession을 가져온다 : 의존성 주입
	private SqlSession session;
	static final String namespace = "com.spring.reply";
	
	@Override
	public void insert(ReplyVO replyVO) throws Exception {
		session.insert(namespace+".insert", replyVO);
		
	}

	@Override
	public void delete(int rNum) throws Exception {
		session.delete(namespace+".delete", rNum);
		
	}

	@Override
	public List<ReplyVO> list(int bNum) throws Exception {
		return session.selectList(namespace+".list", bNum);
	}

	@Override
	public int selectMaxRnum() throws Exception {
		return session.selectOne(namespace+".selectMaxRnum");
	}
	
}
