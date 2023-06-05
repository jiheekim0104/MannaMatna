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
<h1>프로필</h1>
${user}<br>
${user.uiNickname}(${user.uiId})<br>
성별 : 
<a id="uiGender" name="uiGender"></a><br>
연령대 : ${user.uiAge}대<br>
<c:if test="${user.uiFilepath!=null}">
<img src="${user.uiFilepath}" width="300">
</c:if><br>
<button onclick="location.href='/check-update'">정보수정</button>
<button onclick="location.href='/withdraw'">탈퇴하기</button>

<a href="/user-check">유저확인페이지(확인용)</a>
</body>
<script>
if(${user.uiGender}){
	document.querySelector('#uiGender').innerHTML = '남자';
} else {
	document.querySelector('#uiGender').innerHTML = '여자';
}
</script>
</html>