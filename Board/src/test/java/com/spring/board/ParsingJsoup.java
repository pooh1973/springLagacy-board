package com.spring.board;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;


public class ParsingJsoup {
// 네이버 에디터에만 사용가능한 파싱기법이다!!!! 각각의 에디터마다 파싱기법이 봐뀐다.
	
	public String getYoutubeParse(String url){
		String result = "";
		String parsing = "";
		if(url.contains("=")){
//			System.out.println("=을 가지고 있습니다.");
			int index = url.indexOf("=");
			parsing = url.substring(index+1);
			//System.out.println(parsing);
		}else{
//			System.out.println("=을 가지고 있지 않습니다.");
			int index = url.lastIndexOf("/");
			parsing = url.substring(index+1);
			//System.out.println(parsing);
		}
		
		result = result + parsing;
		//System.out.println(result);
		return result;
	}
	
	
	@Test
	public void parseTest(){
		String data = "<p>영상 주소</p><p><a href=\"https://www.youtube.com/watch?v=uFJGdUeC2pk\">https://www.youtube.com/watch?v=uFJGdUeC2pk</a>&nbsp;</p>";
		// 1. youtube라는 단어가 있는지 찾아서 있으면 파싱해준다. : jsoup이용
		Document doc = Jsoup.parse(data);	
		// System.out.println(doc);
		// <a>를 찾아는다 : youtube 단어는 <a>안에 있다
		Elements a_tag = doc.select("a");
		
		int a_tagSize = a_tag.size(); // 여러번 사용되어 변수로 저장
		
		if(a_tagSize > 0){
			for(int i=0; i< a_tagSize; i++){
				if(a_tag.get(i).attr("href").contains("https://www.youtube.com") || a_tag.get(i).attr("href").contains("https://youtu.be")){
					// System.out.println("유투브 영상 링크가 존재합니다.");
					String href = a_tag.get(i).attr("href");
					String result = getYoutubeParse(href);
					// System.out.println(result);
					String iFrame = "<iframe id=\"player\" type=\"text/html\" width=\"90%\" height=\"409\" src=\"http://www.youtube.com/embed/"+result+"\" frameborder=\"0\" webkitallowfullscreen=\"\" mozallowfullscreen=\"\" allowfullscreen=\"\"></iframe>";
					a_tag.get(i).after(iFrame); // a_tag.get(i)뒤에 iFrame붙혀넣음, befor는 앞에 붙혀넣음
				}
			}
		}
		System.out.println(doc);
	}
}
