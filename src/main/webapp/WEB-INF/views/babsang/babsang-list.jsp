<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file= "/WEB-INF/views/common/sideBar.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>만나맛나 : 1인가구를 위한 신개념 밥상 매칭 사이트</title>

<style type="text/css">

</style>

</head>

<body>
<div class="content">
	<h1>여기는 만들어진 밥상 리스트</h1>
	<hr>
	<!-- 밥상 리스트 목록 -->
	<table border="1" style="color: black;">
		<c:if test="${empty babsangList}">
			<th>게시물이 존재하지 않습니다</th>
		</c:if>
		<c:forEach items="${babsangList}" var="babsangListVO">
			<tr>
				<th colspan="2"><a href="/detail/${babsangListVO.biNum}">제목 : ${babsangListVO.biTitle}</a>
				</th>
			</tr>
<!-- 생성자, 보드 번호 추후 삭제 예정 -->
			<tr>
				<td colspan="2">밥상 번호 : ${babsangListVO.biNum}
				</td>
			</tr>
			<tr>
				<td colspan="2">밥상 생성자 : ${babsangListVO.uiNum}
				</td>
			</tr>
<!-- 이 까지 -->
			<tr>
				<td colspan="2">카테고리 : ${babsangListVO.biFdCategory}</td>
			</tr>
			<tr>
				<td colspan="2">최대 인원 수 : ${babsangListVO.biHeadCnt}</td>
			</tr>
			<tr>
				<td>날짜 : ${babsangListVO.biMeetingDat}</td>
				<td>시간 : ${babsangListVO.biMeetingTim}</td>
			</tr>
			<%-- 밥상 삭제 버튼
			<tr>
				<c:if test="${sessionScope.user.uiNum == babsangListVO.uiNum}">
					<td colspan="2" align="center">
						<button
							onclick="location.href='/deleteBabsang?biNum=${babsangListVO.biNum}'">밥상
							삭제</button>
					</td>
				</c:if>
			</tr> --%>
		</c:forEach>
	</table>
</div>	
</body>
</html>