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
	<hr>
	<h2>댓글연동확인</h2>
	<table>
		<tr>
			<th>댓글번호</th>
			<th>내용</th>
			<th>작성날짜</th>
			<th>작성시간</th>
			<th>작성자</th>
			<th>게시물번호</th>
		</tr>
		<c:forEach items="${commentList}" var="commentInfoVO">
			<tr>
				<td>${commentInfoVO.ciNum}</td>
				<td>${commentInfoVO.ciContent}</td>
				<td>${commentInfoVO.ciCredat}</td>
				<td>${commentInfoVO.ciCretim}</td>
				<td>${commentInfoVO.uiNum}</td>
				<td>${commentInfoVO.biNum}</td>
				<td><button>수정하기</button>
				<td><button>삭제하기</button>
			</tr>
		</c:forEach>
	</table>
	<hr>
	<br>
	<!-- 추가추가 -->
	<h2>댓글다른방식연동=babsang-list클릭요청시detail페이지이동 구현시 확인</h2>
	<hr>
	<div class="container">
		<label for="ciContent">comment</label>
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

	<!-- babsang-comment.jsp 연결 -->
	<%@ include file="babsang-comment.jsp"%>
</body>
</html>