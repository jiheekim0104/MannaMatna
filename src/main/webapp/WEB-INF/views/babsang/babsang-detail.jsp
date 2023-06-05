<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>밥상상세페이지</title>
</head>
<body>
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
</div>
	<!-- 추가추가 -->
	<hr>
	<div class="container">
		<label for="ciContent">댓글</label>
		<form name="commentInsertForm">
			<div class="input-group">
				<input type="hidden" name="biNum" value="${detail.biNum}" /> <input
					type="text" class="form-control" id="ciContent" name="ciContent"
					placeholder="내용을 입력하세요."> <span class="input-group-btn">
					<button type="button" name="commentInsertBtn">등록</button>
				</span>
			</div>
		</form>
	</div>

	<div class="container">
		<div class="commentList"></div>
	</div>
	<script src="http://code.jquery.com/jquery-latest.js"></script> 
	<!-- babsang-comment.jsp 연결 -->
	<%@ include file="babsang-comment.jsp"%>
</body>
</html>