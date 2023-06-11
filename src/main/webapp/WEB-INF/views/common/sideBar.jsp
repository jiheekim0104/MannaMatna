<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>만나맛나 : 1인가구를 위한 신개념 밥상 매칭 사이트</title>
<link rel="stylesheet" href="${path}/resources/css/sideBar.css" />
<script src="${path}/resources/js/sideBar.js"></script>
<script src ="https://developers.kakao.com/sdk/js/kakak/min.js"></script>
</head>
<body>
<header>
하위~
</header>

<nav>
	<div class="sidenav">
		<a href="/main"><img src="../../../resources/upload/logo.png" class="logo" ></a>
		<!-- 비로그인 시 -->
		<c:if test="${sessionScope.user.uiId == null}">
			<a href="/login">Login</a>
			<a href="/join">Join</a>
		</c:if>
	
		<!-- 로그인 시 -->
		<c:if test="${sessionScope.user.uiId != null}">
			<a><img class="profileImg" src="${sessionScope.user.uiFilepath}" onclick="location.href='/profile'"></a><br>
			<div class="greetings">
				<span class="uiNickname"> ${sessionScope.user.uiNickname}</span>님 안녕하세요</div><br>

			<a href="/profile">Profile</a>
			<a href="/logout">Logout</a><br>
			<button onclick="location.href='/addBabsang'">밥상 생성</button>
		
			
		</c:if>
	</div> <!-- nav END -->
</nav>

<footer></footer>
</body>
</html>