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
	<button onclick="location.href='/main'">프로필 이미지</button><br>
	<button onclick="location.href='/login'">Login</button><br>
	<button onclick="location.href='/join'">Join</button><br>
	<button onclick="location.href='/createBabsang'">밥상 생성</button><br>
	
	
	<c:forEach items="${babsangList}" var="babsangListVO">
		<table border="1" onclick="location.href='/comment'">
			<c:if test="${empty babsangList}">
		 		<th>게시물이 존재하지 않습니다</th>
			</c:if>
			<tr>
				<th colspan="2">
					${babsangListVO.biTitle}
				</th>
			</tr>
			<tr>
				<td colspan="2">
					${babsangListVO.biFdCategory}
				</td>
			</tr>
			<tr>
				<td colspan="2">
					${babsangListVO.biHeadCnt}
				</td>
			</tr>
			<tr>
				<td>
					${babsangListVO.biMeetingDat}
				</td>
				<td>
					${babsangListVO.biMeetingTim}
				</td>
			</tr>
		</table>
	</c:forEach>
</body>
</html>