package com.spring.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.board.domain.UserVO;
import com.spring.board.persistent.UserDAO;

@Controller
public class UserController {

	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping("/user/joinForm")
	public String userJoinForm(){
		return "/user/joinForm";
	}
	
	@RequestMapping("/user/join")
	public String userJoin(UserVO userVO) throws Exception{
		userDAO.insert(userVO);
		return "redirect:/"; // index로 리다이렉트이동 --> 차후 join페이지로 이동
	}
	
	@RequestMapping("/board/delete")  // view로 페이지 이동만 한다 --> 규칙 : 글쓰기 폼으로 이동 form붙혀준다
	public String userDelete(@RequestParam int userID) throws Exception { // 리퀘스트에서 bNum값 찾아오기
		userDAO.delete(userID);
		return "redirect:/";  //
	}
	
}
