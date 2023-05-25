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
	<h2>밥상상세!!!!!!!!!!!!!!</h2>
	<h2>댓글연동확인</h2>
	<table border="1">
		<tr>
			<th>댓글번호</th>
			<th>내용</th>
			<th>작성날짜</th>
			<th>작성시간</th>
			<th>작성자</th>
			<th>게시물번호</th>
		</tr>
		<c:forEach items="${commentList}" var="commentVO">
			<tr>
				<td>${commentVO.ciNum}</td>
				<td>${commentVO.ciContent}</td>
				<td>${commentVO.ciCredat}</td>
				<td>${commentVO.ciCretim}</td>
				<td>${commentVO.uiNum}</td>
				<td>${commentVO.biNum}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>