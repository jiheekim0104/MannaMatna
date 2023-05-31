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
	<h2>여기는 메인 페이지</h2>
	<button onclick="location.href='/main'">메인로고</button><br>
	<button onclick="location.href='/login'">Login</button><br>
	<button onclick="location.href='/join'">Join</button><br>
	<c:forEach items="${babsangList}" var="babsangListVO">
		<span>${babsangListVO.biTitle}</span>
		<span>${babsangListVO.biHeadCnt}</span>
		<span>${babsangListVO.biFdCategory}</span>
		<span>${babsangListVO.biMeetingDat}</span>
		<span>${babsangListVO.biMeetingTim}</span>
	</c:forEach>
</body>
</html>