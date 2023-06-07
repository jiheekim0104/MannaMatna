<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>만나맛나 : 1인가구를 위한 신개념 밥상 매칭 사이트</title>

<style type="text/css">
.profileImg {
	width: 200px;
	height: 200px;
	object-fit: cover;
	border: 2px solid black;
}
</style>

</head>

<body>
	<h1>여기는 메인 페이지</h1>
	<hr>
	<h2>로그인 정보 확인</h2>
	<p>
		<img class="profileImg" src="${sessionScope.user.uiFilepath}" onclick="location.href='/profile'">
	</p>

	<c:if test="${sessionScope.user.uiId == null}">
		<button onclick="location.href='/login'">Login</button>
		<br>
		<button onclick="location.href='/join'">Join</button>
	</c:if>
	<c:if test="${sessionScope.user.uiId != null}">
		<p>유저 번호 : ${sessionScope.user.uiNum}</p>
		<p>닉네임 : ${sessionScope.user.uiNickname}</p>
		<p>${sessionScope.user.uiNickname}님
			안녕하세요!!!!<br>
			<button onclick="location.href='/logout'">Logout</button>
			<button onclick="location.href='/profile'">Profile</button>
		</p>
	</c:if>
	<p>
		<button onclick="location.href='/addBabsang'">밥상 생성</button>
	</p>

	<table border="1" style="color: black;">
		<c:if test="${empty babsangList}">
			<th>게시물이 존재하지 않습니다</th>
		</c:if>
		<c:forEach items="${babsangList}" var="babsangListVO">
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
			<c:if test="${sessionScope.user.uiNum == babsangListVO.uiNum}">
			<tr>
				<td>
					<button onclick="location.href='/deleteBabsang?biNum=${babsangListVO.biNum}'">밥상 삭제</button>
				</td>
			</tr>
			</c:if>
		</c:forEach>
	</table>
</body>
</html>