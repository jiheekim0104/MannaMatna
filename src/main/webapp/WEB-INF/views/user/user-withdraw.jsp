<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>회원 탈퇴</h1>
 ${user.uiNickname}님,저희를 떠나는 이유를 알려주세요.<br>
<form action="/withdraw" method="post" onsubmit="return checkValue()">
	<input type="text" name="uiDel" id="uiDel" placeholder="탈퇴사유를 적어주세요.">
	<button>확인</button>
</form>	

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