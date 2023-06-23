<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>만나맛나 : 1인가구를 위한 신개념 밥상 매칭 사이트</title>
<link rel="icon" href="../../../resources/upload/favicon.png" type="image/x-icon">
<link rel="stylesheet" href="${path}/resources/css/header.css" />
<link rel="stylesheet" href="${path}/resources/css/sideBar.css" />
<link rel="stylesheet" href="${path}/resources/css/footer.css" />
<script src="${path}/resources/js/sideBar.js"></script>
</head>
<body>

<!-- <div id="loading" class="loading">
</div> 로그인 딜레이 작업해보려고 써둔것임 -->
	<header>
		<div class="mainText">만나맛나</div>
		<div class="subText">1인가구를 위한 신개념 밥상 매칭 사이트</div>
	</header>

	<nav>
		<div class="sidenav">
			<img src="../../../resources/upload/logo(shake).gif"
				class="logo" onclick="location.href='/main'" style="cursor: pointer;">
				
			<!-- 비로그인 시 출력 화면-->
			<c:if test="${sessionScope.user.uiNickname == null}">
				<a href="/login">Login</a>
				<a href="/join">Join</a>
				<!-- 테스트 완료
				<a>세션.uiNum = ${sessionScope.user.uiNum}</a>
				<a>유저.uiNum = ${user.uiNum}</a>
				-->
			</c:if>

			<!-- 로그인 시 출력 화면-->
			<c:if test="${sessionScope.user.uiNickname != null}">
				<div class="box">
					<c:if test="${sessionScope.user.uiFilepath != null}">	
						<img class="profileImg" src="${sessionScope.user.uiFilepath}">
					</c:if>
					<c:if test="${sessionScope.user.uiFilepath == null && sessionScope.user.kakaoImgPath != null}">	
						<img class="profileImg" src="${sessionScope.user.kakaoImgPath}">
					</c:if>
					<c:if test="${sessionScope.user.uiFilepath == null && sessionScope.user.naverImgPath != null}">	
						<img class="profileImg" src="${sessionScope.user.naverImgPath}">
					</c:if>
				</div>
				<br>
				<div class="greetings">
					<span class="uiNickname"> ${sessionScope.user.uiNickname}</span>님
					안녕하세요
				</div>
				<br>
				<a href="/profile">Profile</a>
				<a href="/logout">Logout</a>
				<br>
				
				<!-- 테스트 완료
				<a>세션.biNum = ${sessionScope.user.biNum}</a>
				<a>유저.biNum = ${user.biNum}</a>
				<a>세션.uiNum = ${sessionScope.user.uiNum}</a>
				<a>유저.uiNum = ${user.uiNum}</a>
				-->
				
				<!-- 내가 만든 밥상이 없을 때 보이는 버튼-->
				<c:if test="${user.biNum == 0}">
					<a class="addBabsang" href="/addBabsang">밥상 생성</a>
				</c:if>
				<!-- 내가 만든 밥상이 있을 때 보이는 버튼-->
				<c:if test="${user.biNum != 0}">
					<a class="addBabsang" href="/detail/${user.biNum}">내 밥상<br>보러가기</a>
				</c:if>
				
			</c:if>
			
			
			<!-- 관리자로 로그인 시 출력 화면-->
			<c:if test="${sessionScope.user.uiId=='administer'}">
			<script>
			console.log('${sessionScope.user.uiId}');
			</script>
			<br>
			<!-- 통계 조회 버튼 추가 -->
			<a class="addBabsang" href="/chart">통계 조회</a>
			</c:if>
			
		</div>
		<!-- sidenav END -->
	</nav>
	<footer>Copyright 2023. (만나맛나) All pictures cannot be copied without permission
</footer>

</body>
</html>