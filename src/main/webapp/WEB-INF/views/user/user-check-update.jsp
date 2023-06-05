<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/check-update"  method="POST" onsubmit="return checkValue()">
	${user.uiNickname}님이 맞는지 한번더 확인할게요.<br>
	<input type="password" name="uiPwd" id="uiPwd" placeholder="비밀번호를 입력해주세요.">
	<button>확인</button>
</form>


</body>
<script>
function checkValue(){
	let inputPwd = document.getElementById('uiPwd').value; // 입력받은 비밀번호
	if(inputPwd.trim() == "") {
		alert("비밀번호는 필수입력 사항입니다.");
		return false;
	} 
	if(inputPwd!=${user.uiPwd}){
		alert("비밀번호가 일치하지않습니다.");
		return false; 
	}
	return true;
	}
</script>
</html>