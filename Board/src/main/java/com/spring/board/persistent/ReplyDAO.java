package com.spring.board.persistent;

import java.util.List;

import com.spring.board.domain.ReplyVO;

public interface ReplyDAO {
	void insert(ReplyVO replyVO) throws Exception; // 인터페이스는 앞 선언에 public abstract(추상메소드)가 기본적으로 생략
	void delete(int rNum) throws Exception;
	List<ReplyVO> list(int bNum) throws Exception;
	int selectMaxRnum() throws Exception;
}
