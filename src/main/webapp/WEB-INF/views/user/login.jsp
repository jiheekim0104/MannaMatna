<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	로그인 페이지입니다 거억~
	<br>
	<form action="/index" method="POST" onsubmit="return checkValue()">
		<input type="text" name="uiId" id="uiId" placeholder="아이디를 입력해주세요."><br>
		<div id="uiIdNullMsg" name="uiIdNullMsg" style="color: red"></div>

		<input type="password" name="uiPwd" id="uiPwd" placeholder="비밀번호를 입력해주세요."><br>
		<div id="uiPwdNullMsg" name="uiPwdNullMsg" style="color: red"></div>

		<button>로그인</button><br>
		<button>네이버 로그인</button>
		<button>카카오 로그인</button>
		<br>
	</form>
	<button onclick="location.href='/join'">회원가입</button>

	<script>
		function checkValue() {
			let inputId = document.getElementById('uiId').value; //입력받은 아이디
			let inputPwd = document.getElementById('uiPwd').value; //입력받은 비밀번호
			
			//아이디 입력 확인
			if (inputId == "") {
				document.querySelector('#uiIdNullMsg').innerHTML = '아이디는 필수입력 사항입니다.'
				return false;
			} else if (inputId.length<8||inputId.length>20) {
				document.querySelector('#uiIdNullMsg').innerHTML = '아이디는 8~20자리입니다.'
				return false;
			} else if (inputId != "") {
				document.querySelector('#uiIdNullMsg').innerHTML = null
			}
	
			//비밀번호 입력 확인
			if (inputPwd == "") {
				document.querySelector('#uiPwdNullMsg').innerHTML = '비밀번호는 필수입력 사항입니다.'
				return false;
			} else if (inputPwd.length<4||inputId.length>20) {
				document.querySelector('#uiPwdNullMsg').innerHTML = '비밀번호는 4~20자리입니다.'
				return false;
			} else if (inputPwd != "") {
				document.querySelector('#uiPwdNullMsg').innerHTML = null
			}
			return true;
		}
	</script>
</body>
</html>