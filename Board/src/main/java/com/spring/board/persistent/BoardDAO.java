package com.spring.board.persistent;

import java.util.List;

import com.spring.board.domain.BoardVO;

public interface BoardDAO {
	// 동적바인딩 하기 위하여 인터페이스로 메소드 선언 : 인터페이스는 앞 선언에 public abstract 가 기본적으로 생략 
	void insert(BoardVO board) throws Exception;   // throws Exception : 기본적으로 예외처리 상속
	void delete(int bNum) throws Exception;
	void update(BoardVO board) throws Exception;
	void updateReadCount(int bNum) throws Exception;
	List<BoardVO> list(int page) throws Exception;  // 게시글 여러개 받기
	BoardVO select(int bNum) throws Exception;   // 게시글 한개 받기
}
