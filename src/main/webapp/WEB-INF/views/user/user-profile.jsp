<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ include file= "/WEB-INF/views/common/sideBar.jsp"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>       
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" href="${path}/resources/css/user-profile.css" />
<script>
<c:if test="${msg!=null}">
alert('${msg}');
</c:if>
</script>
<body>
<div class="content">
	<div class="img">
		<c:if test="${user.uiFilepath!=null}">
		<img src="${user.uiFilepath}" width="300">
		</c:if>
	</div>
	<br><br><br>
	<div class="infoBox">
		<div class="info">
			<span class="uiNickname">${user.uiNickname}</span>
			(${user.uiId})<br>
			성별 : 
			<a id="uiGender" name="uiGender"></a><br>
			연령대 : ${user.uiAge}대<br>
		</div>	
		<div class="buttons">
			<button onclick="location.href='/check-update'">정보수정</button>
			<button onclick="location.href='/withdraw'">탈퇴하기</button>
		</div>
	</div>
</div>
</body>
<script>
if(${user.uiGender}){
	document.querySelector('#uiGender').innerHTML = '남자';
} else {
	document.querySelector('#uiGender').innerHTML = '여자';
}
</script>
</html>