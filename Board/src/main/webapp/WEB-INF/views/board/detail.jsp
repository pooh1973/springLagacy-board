<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="../header.jsp"%>

<section id="about">
  <div class="container">
	<a href="/board/updateForm?bNum=${boardVO.bNum}" class="btn btn-primary">수정</a>
	<a href="/board/delete?bNum=${boardVO.bNum}" class="btn btn-danger">삭제</a>
	<div class="row">
	  <div class="col-lg-8 mx-auto">
			<h2 class="text-center"><b>${boardVO.bTitle}</b></h2>
			<hr>
			<p class="lead">${boardVO.bContent}</p>
			<hr>
		</div>
	  <hr>
	  
	  <!-- 댓글 디자인하기 -->
	  <div class="col-lg-8 mx-auto">
			<div class="card my-4">
				<h5 class="card-header">댓글</h5>
				<div class="card-body">
					<textarea class="form-control" rows="3" id="rContent_textarea"></textarea>
					<input type="button" class="btn btn-primary" value="작성완료" onclick="sendReply()"/>
				</div>
			</div>
		</div>
	  <hr>
	  
	  <!-- 댓글 내용 -->
	  <div id="reply" class="col-lg-8 mx-auto">
		<c:forEach var="item" items="${list}">
		  <div>
			<b>${item.userID}</b> 
			<a href='/reply/delete?rNum=${item.rNum}&bNum=${item.bNum}'>
			  <i class='material-icons'>cancel</i>
			</a><br>
			${item.rContent}
		  </div>
		</c:forEach>
	  </div>
	  <!-- END -->	  
	  
	</div>	
  </div>
</section>

<%@include file="../footer.jsp" %>

<!-- Ajax 요청 함수 -->
<script>
	
	function addDiv(rNum, bNum, userID, rContent){  // 뎃글추가함수 prepend -> 최신글이 현재(기준)글 위로(depend-> 최신글이 아래로)
		var newDiv = document.createElement("Div");
//		newDiv.className="col-lg-8 mx-auto";  // css용 클래스네임
		newDiv.innerHTML = "<b>"+userID+"</b> <a href='/reply/delete?rNum="+rNum+"&bNum="+bNum+"'><i class='material-icons'>cancel</i></a><br>"+rContent;
		document.querySelector('#reply').prepend(newDiv);
	}

	function sendReply(){
		// var rContent = document.querySelector('#rContent'); // 엘리먼트 찾기
		// rContent = rContent.textContent; // 해당 엘리먼트의 실제 데이터
		var rContent_textarea = document.querySelector('#rContent_textarea');
		var rContent = rContent_textarea.value;
		// alert(rContent);
		// console.log(rContent);
		if(rContent == ''){
			alert('글을 입력하세요');
			return false; // 함수종료
		}
		// rContent, bNum, userID(admin으로 고정--> 차후 세션값으로 변경) 3개 값을 넘겨줘야 한다(스프링으로 전송)
		var rJson = {
			"rContent":rContent,  // Json형식이 아닌 자바스크립트오브젝트이다 --> Json으로 봐꿔줘야한다 
			// -> JSON.stringify(rJson) --> 문자열로 봐뀌면서 ""붙는다 --> 데이터를 전송할때 JSON으로 봐꿔줘야 한다
			//--> JSON.pavse(변경한문자열) 통신으로 보내면 모두 문자열로 된다 --> JSON으로 변환없이 문자열로 보내면된다.
			"bNum":'${boardVO.bNum}', // 자바스크립트에서는 표현식에 ''을 꼭 붙일것!!
			"userID":'admin'
		};
				
		// console.log(rJson);  // "" 붙기전
		rJson = JSON.stringify(rJson);  // ""붙은 문자열로 봐꿔준다. --> String으로(json형식의) 변환
		// console.log(rJson);  // "" 붙은후
		
		// <<중요!!! P351~359>> 통신시 text(문자열)로 보내고 받아오는 문자열을 Object.parse로 문자열을 처리해서 변경해주면 끝난다.
		// 내가 보낼때는 꼭 dataType:utf-8 을 기술해서 내가 결정해서 보내고 내가 받을때는 서버에서 타입을 결정한다.
		$.ajax({  // (jqury) ajax 통신으로 1개를 보냄
			type:"POST",
			url:"/reply/write",
			dataType:"text",  // 호출되는 서버쪽 함수의 리턴타입  -> rNum이 리턴됨
			contentType:"application/json; charset=utf-8", // 내가 보내(전송)는 데이터타입 --> 헤더에 담아 컨버팅을 위한 정보로 사용됨
			data:rJson, // 내가 전송할 데이터
			success:function(rNum){ // 성공하고 돌아오면 자동으로 호출되는 함수 : aaa -> 리턴되는 값(Object)
				// alert("통신 성공");
				// console.log(data);
/* 				console.log(aaa.key2);
				var test1 = JSON.parse(aaa);
				console.log(test1.key2); */
				
				// 밑에 뿌려줘야 함. --> 성공시점에서 rNum을 가져와야함(삭제시 필요)				
				// alert(rNum)
				addDiv(rNum, '${boardVO.bNum}', 'admin', rContent);  //admin은 나중에 세션값으로 봐꿔줄것
				rContent_textarea.value="";
			},
			error:function(){ // 실패하고 돌아오면 자동으로 호출되는 함수 : aaa -> 리턴되는 값(Object)
				alert("통신실패");
			}
		});		
	}
</script>

