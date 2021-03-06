package com.spring.board.persistent;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.board.domain.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO {
// alt+shift+s --> 오버라이드 all 해준다.
	// 주입 : DB연결을 위해 실행시생성되어 있는 DB객체를 가져온다. root-context.xml에서 id="sqlSession"
	@Inject
	private SqlSession session;
	private static final String namespace = "com.spring.board";
	
	@Override
	public void insert(BoardVO board) throws Exception {
		session.insert(namespace+".insert", board);		
	}

	@Override
	public void delete(int bNum) throws Exception {
		session.delete(namespace+".delete", bNum);		
	}

	@Override
	public void update(BoardVO board) throws Exception {
		session.update(namespace+".update", board);		
	}

	@Override
	public void updateReadCount(int bNum) throws Exception {
		session.update(namespace+".updateReadCount", bNum);
	}

	@Override
	public List<BoardVO> list(int page) throws Exception {
		return session.selectList(namespace+".list", (page-1)*5);
	}

	@Override
	public BoardVO select(int bNum) throws Exception {
		return session.selectOne(namespace+".select", bNum);
	}

}
