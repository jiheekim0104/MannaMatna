<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ include file= "/WEB-INF/views/common/sideBar.jsp"%>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" href="${path}/resources/css/user-check.css" />
<body>
<div class="content">
		<form action="/withdraw" method="post" onsubmit="return checkValue()">
		<br><br>
			<div class="inform">
			 	<span class="uiNickname">${user.uiNickname}</span>님,저희를 떠나는 이유를 알려주세요.<br>
			 </div>
			<textarea name="uiDel" id="uiDel" placeholder="탈퇴사유를 적어주세요."></textarea>
			<button class="withdrawBnt">확인</button>
		</form>	
	</div>
</body>
<script>
	function checkValue(){
		let inputDel = document.getElementById('uiDel').value; // 입력받은 탈퇴사유
		if(inputDel.trim() == ""){
			alert("탈퇴사유를 입력해주세요.");
			return false;
		}
		return true;
	}
</script>
</html>