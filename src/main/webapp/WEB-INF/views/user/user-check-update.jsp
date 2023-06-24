<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ include file= "/WEB-INF/views/common/sideBar.jsp"%>
<%@ include file="/WEB-INF/views/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" href="${path}/resources/css/user-check.css" />
<script>
<c:if test="${msg!=null}">
alert('${msg}');
</c:if>
</script>
<body>
	<div class="content">
		<form action="/check-update"  method="POST" onsubmit="return checkValue()">
		<br><br>
			<div class="inform">
				<span class="uiNickname">${user.uiNickname}</span>님이 맞는지 한번더 확인할게요.<br>
			</div>	
			<input type="password" class="uiPwd" name="uiPwd" id="uiPwd" placeholder="비밀번호를 입력해주세요.">
			<button class="updateBnt">확인</button>			
		</form>
	</div>
</body>
<script>
function checkValue(){
	let inputPwd = document.querySelector('.uiPwd').value; // 입력받은 비밀번호
	if(inputPwd.trim() == "") {
		alert("비밀번호는 필수입력 사항입니다.");
		return false;
	} 

	return true;
	}
</script>
</html>