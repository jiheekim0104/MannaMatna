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
<h1> 여기는 메인입니다. </h1>
<c:if test="${user.uiId == null}">
<a href="/login">로그인</a>
</c:if>

<c:if test="${user.uiId != null}">
${user.uiNickname}님 안녕하세요<br>
<a href="/logout">로그아웃</a>
<a href="/profile">프로필</a>
</c:if>


</body>
</html>