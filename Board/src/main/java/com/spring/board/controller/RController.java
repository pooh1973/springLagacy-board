package com.spring.board.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.board.domain.ReplyVO;
import com.spring.board.persistent.ReplyDAO;

class Animal {
	private String name;
	private String type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}

@Controller
public class RController {
	
	@Inject
	private ReplyDAO replyDAO;
	
	@RequestMapping(value="/reply/write", method=RequestMethod.POST)
	// @RequestBody : 뷰리졸브가 아닌 메시지컨버터가 데이터를 리턴해준다
	// public @ResponseBody JSONObject replyWrite(){
	public @ResponseBody String replyWrite(@RequestBody ReplyVO reply) throws Exception{
		//System.out.println(reply.getrContent());
		
		replyDAO.insert(reply);
		int rNum = replyDAO.selectMaxRnum();
		return rNum+"";  // String으로 묵시적 형변환  --> 통신은 byte String 
		
	}
	
	@RequestMapping("/reply/delete")
	public String replyDelete(@RequestParam int rNum, @RequestParam int bNum, RedirectAttributes redirect) throws Exception{
		replyDAO.delete(rNum);
		redirect.addAttribute("bNum", bNum); // redirect로 파라미터 넘기기
		return "redirect:/board/detail"; 
	}
	
}






/*class Animal {
private String name;
private String type;

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}

}

@Controller
public class test_old_RController {

// produces="application/text; charset=utf-8" :  마임타입 --> 문자열을 보낼때만 사용
// @RequestBody : 뷰리졸브가 아닌 메시지컨버터가 데이터를 리턴해준다(데이터관리)
@RequestMapping(value="/reply/write", method=RequestMethod.POST)
public @ResponseBody Animal replyWrite(){
	Animal a = new Animal();
	a.setName("toto");
	a.setType("maltiz");
	return a;  // 객체(Object)를 리턴할경우 처리방법
}

public void getData(){
	
	// 자바 필요한 부분만 파싱
	String json = "";
	JSONParser p = new JSONParser();
	
	JSONObject jsonObj;
	try {
		jsonObj = (JSONObject)p.parse(json);
		JSONArray arr = (JSONArray)jsonObj.get("response");
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	Gson gson = new Gson();
	String str = gson.toJson("자바 클래스");  // 자바 클래스를 제이슨으로 변경
	Animal a = gson.fromJson(str, Animal.class);  // 제이슨은 자바 오브젝트로 변경 
}
}*/

/*@Controller
public class RController {

@RequestMapping(value="/reply/write", method=RequestMethod.POST, produces="application/text; charset=utf-8")
// @RequestBody : 뷰리졸브가 아닌 메시지컨버터가 데이터를 리턴해준다
// public @ResponseBody JSONObject replyWrite(){
public @ResponseBody String replyWrite(){
		
	Map<String, String> result = new HashMap<String, String>();
	result.put("key", "bitc5600");
	System.out.println("맵입니다.");
	System.out.println(result.toString());
	
	JSONObject json = new JSONObject();
	json.put("key2", "my1234");
	System.out.println("json입니다.");
	System.out.println(json.toString());
	return json.toString();
}
}*/