<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>     
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
여기는 메인입니다. 거억

<a href="/login">로그인</a>
<table border="1">
		<tr>
			<th>번호</th>
			<th>이름</th>
			<th>아이디</th>
			<th>가입일</th>
		</tr>
		<c:forEach items="${userList}" var="userVO">
			<tr>
				<td>${userVO.uiNum}</td>
				<td>${userVO.uiNickname}</td>
				<td>${userVO.uiId}</td>
				<td>${userVO.uiCredat}</td>
			
			</tr>
		</c:forEach>
	</table>

</body>
</html>