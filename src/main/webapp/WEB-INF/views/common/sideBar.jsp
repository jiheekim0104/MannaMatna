<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>만나맛나 : 1인가구를 위한 신개념 밥상 매칭 사이트</title>
<link rel="stylesheet" href="${path}/resources/css/test.css" />
<script src="${path}/resources/js/test.js"></script>
</head>
<header>
	<ul>
		<li><a class="home" href="/main">로고들어갈자리</a></li>
		<!-- 비로그인 시 -->
		<c:if test="${sessionScope.user.uiId == null}">
			<li><a href="/login">Login</a></li>
			<li><a href="/join">Join</a></li>
		</c:if>
		<!-- 로그인 시 -->
		<c:if test="${sessionScope.user.uiId != null}">
			<li><img class="profileImg"
				src="${sessionScope.user.uiFilepath}"
				onclick="location.href='/profile'"></li>
			<li>유저 번호 : ${sessionScope.user.uiNum}</li>
			<li>닉네임 : ${sessionScope.user.uiNickname}</li>
			<li>${sessionScope.user.uiNickname}님안녕하세요!!!!<br>
				<button onclick="location.href='/logout'">Logout</button>
				<button onclick="location.href='/profile'">Profile</button>
			</li>
			<li>
				<button onclick="location.href='/addBabsang'">밥상 생성</button>
			</li>
		</c:if>
	</ul>
</header>
</html>