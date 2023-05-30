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
	<h1>밥상상세!!!!!!!!!!!!!!</h1>
	<h2>여기는 밥상 상세 내역보기</h2>
	<hr>
	<h2>댓글연동확인</h2>
	<span>댓글번호</span>
	<span>내용</span>
	<span>작성날짜</span>
	<span>작성시간</span>
	<span>작성자</span>
	<span>게시물번호</span>
	<br><hr><br>
	<c:forEach items="${commentList}" var="commentVO">
		<span>${commentVO.ciNum}</span>
		<span>${commentVO.ciContent}</span>	
		<span>${commentVO.ciCredat}</span>
		<span>${commentVO.ciCretim}</span>
		<span>${commentVO.uiNum}</span>
		<span>${commentVO.biNum}</span>
		<form>
		<button>수정하기</button>
		<button>삭제하기</button>
		</form>
		<br>
	</c:forEach>
	<form>
		<input type="text" id="content" name="content"
			placeholder="내용을 입력해주세요.">
		<button>작성하기</button>
	</form>
</body>
</html>