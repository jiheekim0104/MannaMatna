<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file= "/WEB-INF/views/common/sideBar.jsp"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>밥상상세페이지</title>
</head>
<body>
<div class="content">
	<h1>밥상상세!!!!!!!!!!!!!!</h1>
	<h2>여기는 밥상 상세 내역보기</h2>
	<div>
      <div>
        <label>제목</label>
        <p>${detail.biTitle}</p>
      </div>
      <div>
        <label>작성자</label>
        <p>${detail.uiNum}</p>
      </div>
      <div>
        <label>작성날짜</label>
        <p>${detail.biCreateDat}</p>
      </div>
      <div>
        <label>내용</label>
        <p>${detail.biContent}</p>
      </div>
      <button type="submit">참가하기</button>
      <p>세션스코프 uiNum = ${sessionScope.user.uiNum}</p>
      <p>babsangInfo의 uiNum = ${detail.uiNum}</p>
      <!-- 이 부분 추가했습니다 -->
      <c:if test="${sessionScope.user.uiNum == detail.uiNum}">
		<button onclick="location.href='/deleteBabsang?biNum=${detail.biNum}'">밥상삭제</button>
	  </c:if>
	  
</div>
	<hr>
	<!-- 참가자정보 영역 -->
	<h2>참가자정보 확인</h2>
	<p>작성자정보 ${detail.uiNum}</p>
	<img src ="">
	<hr>
	<h2>로그인유저정보 확인</h2>
	<p>${sessionScope.user.uiNum}</p>
	<img src="${sessionScope.user.uiFilepath}" width="300">
	<p>${sessionScope.user.uiNickname}</p>
	<hr>
	<!-- 댓글영역 -->
	<h3>댓글</h3>
	<div class="container">
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