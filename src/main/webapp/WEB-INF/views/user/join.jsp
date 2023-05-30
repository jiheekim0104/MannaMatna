<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	하위 여기서 가입하면됨이야. <br>
	SNS 연동<br>
	<form action="/" method="POST">
		<input type="text" name="uiId" id="uiId" placeholder="아이디">
		<button onclick="idDuplicationCheck()">중복확인</button>
		<br>
		<input type="text" name="uiNickname" id="uiNickname" placeholder="닉네임">
		<button>중복확인</button>
		<br>
		<input type="password" name="uiPwd" id="uiPwd" placeholder="비밀번호"><br>
		<input type="password" name="uiPwdCheck" id="uiPwdCheck" placeholder="비밀번호 확인"><br> 
		<input type="text" name="uiPhoto" id="uiPhoto" placeholder="프로필 사진">
		<button>업로드</button>
		<br> 
		<select name="uiAge" id="uiAge">
			<option value="none">연령대를 선택하세요.</option>
			<option value="10">10대</option>
			<option value="20">20대</option>
			<option value="30">30대</option>
			<option value="40">40대</option>
			<option value="50">50대 이상</option>
		</select> 
		<input type="radio" name="uiGender" id="uiGender" value="1">남자
		<input type="radio" name="uiGender" id="uiGender" value="2">여자
		<button>가입완료</button>
	</form>


</body>
<script>
	function idDuplicationCheck(){
		let inputId = document.getElementById('uiId').value;
		window.open("${contextPath}/idDuplicationCheck?")
	}
</script>
</html>