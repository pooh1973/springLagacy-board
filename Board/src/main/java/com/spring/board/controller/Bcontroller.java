package com.spring.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.board.domain.BoardVO;
import com.spring.board.domain.ReplyVO;
import com.spring.board.persistent.BoardDAO;
import com.spring.board.persistent.ReplyDAO;
import com.spring.board.util.MyUtiles;

// Controller, Repository, Service, Component
@Controller
public class Bcontroller {
	
	@Autowired   // 스프링 툴이 가지고있는 @  --> @Inject 와 동일 : java가 가지고 있는 툴@
	private BoardDAO boardDAO;
	
	@Autowired
	private ReplyDAO replyDAO;

	// dispatch의 역활  ==> http://localhost:8000/board/
	@RequestMapping("/") // 주소를 매핑 시켜주는 에노테이션(default=get)
	public String index(){
		return "index"; // "/"를 호출하면 index페이지로 리턴
	}
	
	@RequestMapping("/board/list")
	public String boardList(Model model, @RequestParam(defaultValue="1") int page) throws Exception{
		// @RequestParam(defaultValue="1") --> Request파라미터 값이 들어오지 않을경우 디폴트 값으로 1을 넣어준다.
		List<BoardVO> list = boardDAO.list(page);  
		model.addAttribute("list", list); // 컨트롤러가 모델에 주입하여 view까지만 살아서 이동
		model.addAttribute("page", page); // page를 model에 싣어준다 
		return "/board/list";
	}
	
	@RequestMapping("/board/writeForm")  // view로 페이지 이동만 한다 --> 규칙 : 글쓰기 폼으로 이동 form붙혀준다
	public String boardWriteForm() throws Exception { 
		return "/board/writeForm";
	}
	
	@RequestMapping(value="/board/write", method= RequestMethod.POST)
	public String boardWrite(BoardVO brBoardVO  // 데이터 여러건일때 사용 --> VO로 만들어서 필요한 값만 사용
//			@RequestParam String bTitle,  // 리퀘스트값 찾아오기: 데이터 한건 받을경우 사용 하기 용이
			) throws Exception {	
		boardDAO.insert(brBoardVO);  // brBoardVO에 입력받은값 주입
		return "redirect:/board/list"; // redirect: : 뷰리졸브가 viwe로 가는것이 아닌 함수호출로 인식 --> list함수호출
	}
	
	@RequestMapping("/board/delete")  // view로 페이지 이동만 한다 --> 규칙 : 글쓰기 폼으로 이동 form붙혀준다
	public String boardDelete(@RequestParam int bNum) throws Exception { // 리퀘스트에서 bNum값 찾아오기
		boardDAO.delete(bNum);
		return "redirect:/board/list";  // redirect: list함수호출
	}
	
	@RequestMapping("/admin/board/delete")  // admin만 삭제하는 view로 페이지 이동만 한다
	public String adminBoardDelete(@RequestParam int bNum, HttpSession session) throws Exception{	
		boardDAO.delete(bNum);
		return "redirect:/board/list";  // redirect: list함수호출
	}

	
	@RequestMapping("/board/detail")  // view로 페이지 이동만 한다 --> 규칙 : 글쓰기 폼으로 이동 form붙혀준다
	public String boardDetail(@RequestParam int bNum, Model model) throws Exception { // 리퀘스트에서 bNum값 찾아오기
		boardDAO.updateReadCount(bNum);  // 리드카운트 올리기
		BoardVO boardVO = boardDAO.select(bNum); // DB내용 가져오기
		// DB를 하나만 변경시킨 --> 서비스로 변경 불필요 vs 둘다 DB변경이 일어난다면 @TranSactional을 붙혀 서비스로 처리
		
		// 뎃글 뿌려주기(최신글이 위로) bNum값을 가져와서 replyDAO값을 모델로 가져와 뿌려준다
		List<ReplyVO> list = replyDAO.list(bNum);
		String result = MyUtiles.getYoutubeMovie(boardVO.getbContent()); 
		boardVO.setbContent(result);  // boardVO의 내용에 result값 셋팅
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("list", list);
		return "/board/detail";  // redirect: list함수호출
	}
	
	@RequestMapping("/board/updateForm")  // view로 페이지 이동만 한다 --> 규칙 : 수정하기폼으로 이동 form붙혀준다
	public String boardUpdateForm
	(@RequestParam int bNum, Model model) throws Exception{	 //get으로 리퀘스트로 받아 model에 담아 넘어감)
		BoardVO boardVO = boardDAO.select(bNum);
		model.addAttribute("boardVO", boardVO);
		return "/board/updateForm";
	}
	
	@RequestMapping(value="/board/update", method= RequestMethod.POST)
	public String boardUpdate(BoardVO boardVO) throws Exception{
		boardDAO.update(boardVO);  // brBoardVO에 입력받은값 주입
		return "redirect:/board/list"; // redirect: : 뷰리졸브가 viwe로 가는것이 아닌 함수호출 --> list함수호출
	}
}
