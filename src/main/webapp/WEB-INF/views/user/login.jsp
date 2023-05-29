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
	<form action="/index" method="POST">
		<input type="text" name=uiId id=uiId placeholder="아이디"><br>
		<input type="password" name=uiPwd id=uiPwd placeholder="비밀번호"><br>
		<button>로그인</button><br>
		<button>네이버 로그인</button>
		<button>카카오 로그인</button><br>
	</form>
		<button onclick="location.href='/join'">회원가입</button>

</body>
</html>