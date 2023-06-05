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
	<h1>여기는 메인 페이지</h1>
	<button onclick="location.href='/main'">프로필 이미지</button>
	<br>
	<button onclick="location.href='/login'">Login</button>
	<br>
	<button onclick="location.href='/join'">Join</button>
	<br>
	<button onclick="location.href='/createBabsang'">밥상 생성</button>
	<br>

	<table border="1">
		<c:if test="${empty babsangList}">
			<th>게시물이 존재하지 않습니다</th>
		</c:if>
		<!-- onclick 함수는 테이블 자체에 주지않을게용! 
		 각 게시물마다 밥상번호가 다 다르게 넘어가야함! 확인하시면 지우셔도됩니당
	-->
		<c:forEach items="${babsangList}" var="babsangListVO">
			<!-- 일단은 제목에 링크넣었습니다. -->
			<tr>
				<th colspan="2"><a href="/detail/${babsangListVO.biNum}">${babsangListVO.biTitle}</a>
				</th>
			</tr>
			<tr>
				<td colspan="2">${babsangListVO.biFdCategory}</td>
			</tr>
			<tr>
				<td colspan="2">${babsangListVO.biHeadCnt}</td>
			</tr>
			<tr>
				<td>${babsangListVO.biMeetingDat}</td>
				<td>${babsangListVO.biMeetingTim}</td>
			</tr>
		</c:forEach>
	</table>
	<hr>
	<h2>로그인정보확인</h2>
	<p>
</body>
</html>