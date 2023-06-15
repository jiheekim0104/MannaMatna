<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/sideBar.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>밥상상세페이지</title>
<link rel="stylesheet" href="${path}/resources/css/babsang-detail.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<div class="content">
		<div class="title">${detail.biTitle}</div>
		<div class="category">${detail.biFdCategory}</div>
		<hr class="line">
		<c:set var="biMeetingTim" value="${detail.biMeetingTim}" />
		<c:set var="biMeetingDat" value="${detail.biMeetingDat}" />
		<div class="time">맛날시간 : ${fn:substring(biMeetingDat,0,4)}.
			${fn:substring(biMeetingDat,5,7)}. ${fn:substring(biMeetingDat,8,10)}
			${fn:substring(biMeetingTim,0,2)}시 ${fn:substring(biMeetingTim,3,5)}분</div>
		<div class="innerContent">${detail.biContent}</div>
		<c:if test="${sessionScope.user.uiNum != detail.uiNum}">
			<button type="submit">참가하기</button>
		</c:if>
		<!-- 이 부분 추가했습니다 -->
		<c:if test="${sessionScope.user.uiNum == detail.uiNum}">
			<button type="submit">마감하기</button>
			<%-- 프론트작업중 잠깐 주석 처리 
		<button onclick="location.href='/deleteBabsang?biNum=${detail.biNum}'">밥상삭제</button>--%>
		</c:if>

		<hr>
		<!-- 참가자정보 영역 -->

		<div class="participents">참여자정보</div>
		<br>
		<div class="userCount">${fn:length(babsangUserList)}(현재인원)/
			${detail.biHeadCnt} (최대인원)</div>
		<c:forEach items="${babsangUserList}" var="userList">
			<div class="box">
				<img class="profileImg" src="${userList.uiFilepath}"
					onclick="location.href='/profile'">
				<div class="nickName">${userList.uiNickname}</div>
			</div>
		</c:forEach>

		<div class="container">
			<hr>
			<!-- 댓글영역 -->
			<h3>댓글</h3>
			<div class="commentList"></div>
		</div>
		<!-- 제이쿼리가 제대로 로드 되지 않는 현상 해결 -->
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<!-- 댓글입력 Form -->
		<div class="container">
			<label for="ciContent"></label>
			<form name="commentInsertForm">
				<div>
					<input type="hidden" name="biNum" value="${detail.biNum}" /> <input
						type="text" class="form-control" id="ciContent" name="ciContent"
						placeholder="댓글을 입력하세요."> <span class="input-group-btn">
						<button type="button" name="commentInsertBtn">작성하기</button>
					</span>
				</div>
			</form>
		</div>
		<!-- babsang-comment.jsp 연결 -->
		<%@ include file="babsang-comment.jsp"%>
	</div>
</body>
</html>