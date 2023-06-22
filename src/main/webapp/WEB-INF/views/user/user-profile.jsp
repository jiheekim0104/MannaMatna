<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>    
<%@ include file= "/WEB-INF/views/common/sideBar.jsp"%>
<%@ include file="/WEB-INF/views/common/common.jsp"%>
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
${user}
<div class="content">
	<div class="img">
		<c:if test="${user.uiFilepath != null}">	
			<img src="${user.uiFilepath}" width="300">
		</c:if>
		<c:if test="${user.uiFilepath == null && user.kakaoImgPath != null}">	
			<img src="${user.kakaoImgPath}" width="300">
		</c:if>
		<c:if test="${user.uiFilepath == null && user.naverImgPath != null}">	
			<img src="${user.naverImgPath}" width="300">
		</c:if>
	</div>
	<br><br><br>
	<div class="infoBox">
		<div class="info">
			<span class="uiNickname">${user.uiNickname}</span>
			<span class="uiId">(${user.uiId})</span>
			<br>
			성별 : 
			<a id="uiGender" name="uiGender"></a><br>
			연령대 : ${user.uiAge}대<br>
		</div>
		<br><br><br><br>
		<c:if test="${sessionScope.user.uiNum==user.uiNum}">
		<%-- 해당 버튼들은 상세페이지에서 다른 유저의 정보를 보는 경우 수정 및 탈퇴가 불가능하도록 처리 --%>
		<div class="buttons">
			<button class="updateBnt" onclick="location.href='/check-update'">정보수정</button>
			<button class="withdrawBnt" onclick="location.href='/withdraw'">탈퇴하기</button>
			<div class="hint"></div>
			<%
		String clientId = "BSeMnF9B1CusMX9DeEg8";//애플리케이션 클라이언트 아이디값";
   		String redirectURI = URLEncoder.encode("http://localhost/naverPost", "UTF-8");
   		SecureRandom random = new SecureRandom();
    	String state = new BigInteger(130, random).toString();
    	String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code"
         + "&client_id=" + clientId
         + "&redirect_uri=" + redirectURI
         + "&state=" + state;
    	session.setAttribute("state", state);
		%>
		<button class="naverBnt" onclick="location.href='<%=apiURL%>'"><img src="../../../resources/upload/naverLogo.png"> 네이버 간편가입</button>
		<button class="kakaoBnt"onclick="location.href='https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=b288a9632f49edf850cff8d6eb985755&redirect_uri=http://localhost/kakaoPost/'"><img src="../../../resources/upload/kakaoLogo.png"> 카카오 간편가입</button>
		
		</div>
		</c:if>
	</div>
</div>
</body>
<script>
if(${user.uiId==null}){
	document.querySelector('.naverBnt').style.display = "none";
	document.querySelector('.kakaoBnt').style.display = "none";
}
if(${user.uiId!=null} && ${user.kuiId!=0}){ 
	document.querySelector('.uiId').innerHTML = '(${user.uiId}, 카카오 가입 연동)';
} 
if(${user.uiId!=null} && ${user.nuiId!=null}){ 
	document.querySelector('.uiId').innerHTML = '(${user.uiId}, 네이버 가입 연동)';
} 
if(${user.uiId!=null} && (${user.kuiId!=0}||${user.nuiId!=null})){
	document.querySelector('.naverBnt').style.display = "none";
	document.querySelector('.kakaoBnt').style.display = "none";
}
if(${user.uiId==null}){
	document.querySelector('.uiId').innerHTML = '(SNS가입 연동)';
	document.querySelector('.hint').innerHTML = '* SNS가입 연동고객의 초기 비밀번호는 0000 입니다.';
}

if(${user.uiGender}){
	document.querySelector('#uiGender').innerHTML = '남자';
} else {
	document.querySelector('#uiGender').innerHTML = '여자';
}
</script>
</html>