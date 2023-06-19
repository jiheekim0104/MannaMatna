<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>	
<%@ include file= "/WEB-INF/views/common/sideBar.jsp"%>
<link rel="stylesheet" href="${path}/resources/css/login.css" />
<script>
<c:if test="${msg!=null}">
alert('${msg}');
</c:if>
</script>
<html>
<body>
	<div class="content">
	<br><br><br><br><br>
	<form action="/login" method="POST" onsubmit="return checkValue()">
		<input type="text" name="uiId" id="uiId" placeholder="아이디를 입력해주세요."><br>
		<div id="uiIdNullMsg" name="uiIdNullMsg"></div>

		<input type="password" name="uiPwd" id="uiPwd" placeholder="비밀번호를 입력해주세요."><br>
		<div id="uiPwdNullMsg" name="uiPwdNullMsg" style="color: red"></div>

		<button class="loginBnt">로그인</button><br>	
	</form>
	<%
	String clientId = "BSeMnF9B1CusMX9DeEg8";//애플리케이션 클라이언트 아이디값";
    String redirectURI = URLEncoder.encode("http://localhost/naverLogin", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code"
         + "&client_id=" + clientId
         + "&redirect_uri=" + redirectURI
         + "&state=" + state;
    session.setAttribute("state", state);
	%>
	<button class="naverBnt" onclick="location.href='<%=apiURL%>'"><img src="../../../resources/upload/naverLogo.png">네이버 로그인</button>
	<button class="kakaoBnt" onclick="location.href='https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=b288a9632f49edf850cff8d6eb985755&redirect_uri=http://localhost/kakaoLogin/'"><img src="../../../resources/upload/kakaoLogo.png">카카오 로그인</button>
	<br>
	<button class="joinBnt" onclick="location.href='/join'">회원가입</button>

	</div>
	
	
</body>
<script>
		function checkValue() {
			let inputId = document.getElementById('uiId').value; //입력받은 아이디
			let inputPwd = document.getElementById('uiPwd').value; //입력받은 비밀번호
			
			//아이디 입력 확인
			if (inputId.trim() == "") {
				document.querySelector('#uiIdNullMsg').innerHTML = '아이디는 필수입력 사항입니다.'
				return false;
			} else if (inputId.trim().length<8||inputId.trim().length>20) {
				document.querySelector('#uiIdNullMsg').innerHTML = '아이디는 8~20자리입니다.'
				return false;
			} else if (inputId.trim() != "") {
				document.querySelector('#uiIdNullMsg').innerHTML = null
			}
	
			//비밀번호 입력 확인
			if (inputPwd.trim() == "") {
				document.querySelector('#uiPwdNullMsg').innerHTML = '비밀번호는 필수입력 사항입니다.'
				return false;
			} else if (inputPwd.trim().length<4||inputId.trim().length>20) {
				document.querySelector('#uiPwdNullMsg').innerHTML = '비밀번호는 4~20자리입니다.'
				return false;
			} else if (inputPwd.trim() != "") {
				document.querySelector('#uiPwdNullMsg').innerHTML = null
			}
			return true;
		}
	</script>
</html>