<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>	
<%@ include file= "/WEB-INF/views/common/sideBar.jsp"%>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${path}/resources/css/join.css" />
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<div class="content">
	<h1>회원가입</h1>
	<span>SNS 연동</span>
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
	<br>
	<form action="/join-ok" method="POST" onsubmit="return checkValue()" enctype="multipart/form-data">
		<input type="text" name="uiId" id="uiId" placeholder="아이디" onkeyup="idBntChange()">
		<button type="button" id="idChk" class="bnt" onclick="fn_idChk()" value="N" >중복확인</button><br> 
		<input type="text" name="uiNickname" id="uiNickname" placeholder="닉네임" onkeyup="nicknameBntChange()">
		<button type="button" id="nicknameChk" class="bnt" onclick="fn_nicknameChk()" value="N">중복확인</button><br> 
		<input type="password" class="uiPwd" name="uiPwd" id="uiPwd" placeholder="비밀번호"><br> 
		<input type="password" class="uiPwdCheck" name="uiPwdCheck" id="uiPwdCheck" placeholder="비밀번호 확인"><br>
		<input type="text" class="uiPhone" name="uiPhone" id="uiPhone" placeholder="휴대전화(-를 제외하고 입력)"> 
		<button type="button" id="phoneChk" class="bnt" onclick="fn_phoneChk()" value="N" >본인인증</button><br> 
		<input type="text" class="phonePass" name="phonePass" id="phonePass" placeholder="인증번호 입력"> 
		<button type="button" id="phonePassChk" class="bnt" onclick="fn_phonePassChk()" value="N" >인증확인</button><br> 
		<div class="filebox">
    		<input class="upload-name" placeholder="프로필 사진" readonly="readonly">
    		<label for="file">업로드</label> 
   		 	<input type="file" id="file" name="uiFile" accept="image/*" onchange="loadImg(this)">
		</div>
		<div id="imgDiv" style="display:none">
			<img src = "" width="300">
		</div>
		<select name="uiAge" id="uiAge">
			<option value="0">연령대를 선택하세요.</option>
			<option value="10">10대</option>
			<option value="20">20대</option>
			<option value="30">30대</option>
			<option value="40">40대</option>
			<option value="50">50대 이상</option>
		</select><br> 
		<span>성별</span> <input class="gender" type="radio" name="uiGender" id="uiGender1" value="true"> 남자
		<input class="gender" type="radio" name="uiGender" id="uiGender2" value="false"> 여자<br>
		<button class="joinBnt">가입완료</button>
		<input type="hidden" id="smsConfirmNum">
	</form>
</div>
</body>
<script>	
	function idBntChange(){
		$('#idChk').val('N'); // 중복확인 이후에 다시 아이디를 바꿨을 경우에 중복확인을 하지않은 상태로 바꿈 
		$('#idChk').removeClass('selected');
/* 		document.getElementById("idChk").style.backgroundColor = "#FFFFFF";
		document.getElementById("idChk").style.color = "#FC522F";
		document.getElementById("idChk").style.border = "2px solid #FC522F";
		document.getElementById("idChk").style.pointerEvents = "auto"; */	
		//호버기능까지 되돌릴수없나? 그래서 addClass 와 removeClass 활용함!
	}
	
	function nicknameBntChange(){
		$('#nicknameChk').val('N'); // 중복확인 이후에 다시 닉네임을 바꿨을 경우에 중복확인을 하지않은 상태로 바꿈 
		$('#nicknameChk').removeClass('selected');
	}
	
	function loadImg(obj){
		let file =obj.files[0];
		let imgObj = document.querySelector('#imgDiv>img');
		imgObj.src = URL.createObjectURL(file);
		document.querySelector('#imgDiv').style.display='';
		let fileName = $("#file").val().split('/').pop().split('\\').pop();
		$(".upload-name").val(fileName);
	}
	function checkValue(){
		let idChk = document.getElementById('idChk').value; // 아이디 중복확인 시행 유무
		if(idChk=="N"){
			alert("아이디 중복확인을 해주세요.");
			return false;
		}
		let nicknameChk = document.getElementById('nicknameChk').value; // 아이디 중복확인 시행 유무
		if(nicknameChk=="N"){
			alert("닉네임 중복확인을 해주세요.");
			return false;
		}
		
		let inputPwd = document.getElementById('uiPwd').value; // 입력받은 비밀번호
		if(inputPwd.trim() == "") {
			alert("비밀번호는 필수입력 사항입니다.");
			return false;
		} 
		
		if(inputPwd.trim().length<4||inputPwd.trim().length>20) {
			alert("비밀번호는 4~20자리입니다.");
			return false;
		} 
		
		let inputPwdCheck = document.getElementById('uiPwdCheck').value; // 입력받은 확인용 비밀번호
		if(inputPwdCheck.trim() == ""){  
			alert("비밀번호를 한번더 확인해주세요.");
			return false;
		}
		
		if(inputPwd!=inputPwdCheck){
			alert("입력한 비밀번호와 확인용 비밀번호가 일치하지 않습니다.");
			return false;
		}
		
		let phoneChk = document.getElementById('phoneChk').value; // 휴대폰 본인인증 시행 유무
		if(phoneChk=="N"){
			alert("휴대폰 본인인증을 해주세요.");
			return false;
		}
		
		let phonePassChk = document.getElementById('phonePassChk').value; // 휴대폰 인증번호 일치 확인 결과
		if(phonePassChk=="N"){
			alert("인증번호가 일치하지 않습니다.");
			return false;
		}
		
		
		let inputAge = document.getElementById('uiAge').value; // 입력받은 연령대
		if(inputAge == 0){  
			alert("연령대를 선택해주세요.");
			return false;
		}
		
		
		let inputGender = document.querySelector('input[name="uiGender"]:checked'); // 입력받은 성별
		if(inputGender==null){  
			alert("성별을 선택해주세요.");
			return false;
		}
		
		return true;
	}
	
	function fn_idChk() {
		let inputId = document.getElementById('uiId').value; // 입력받은 아이디
		if (inputId.trim().length == "") {
			alert("아이디를 입력해주세요.");
		} else if (inputId.trim().length < 8 || inputId.trim().length > 20) {
			alert("아이디는 8~20자리입니다.");
		} else {
			$.ajax({
				url : "/idChk", // 요청서버 url
				type : "post", // 타입
				contentType : "application/json", // 보내는 데이터의 타입
				data : JSON.stringify({"uiId" : $("#uiId").val()}),// 보낼데이터의 타입
				success : function(data) { // 결과 성공 콜백함수
					if (data.result == 1) {
						alert("중복된 아이디입니다.");
					} else if (data.result == 0) {
						$('#idChk').val('Y');
						alert("사용가능한 아이디입니다.");
						$('#idChk').addClass('selected');
/* 						document.getElementById("idChk").style.backgroundColor = "#c8c8c8";
						document.getElementById("idChk").style.color = "#FFFFFF";
						document.getElementById("idChk").style.border = "2px solid #c8c8c8";
						document.getElementById("idChk").style.pointerEvents = "none"; */
					} 
				}
			})
		}

	}
	function fn_nicknameChk() {
		let inputNickname = document.getElementById('uiNickname').value; // 입력받은 닉네임
		if (inputNickname.trim().length == "") {
			alert("닉네임을 입력해주세요.");
		} else if (inputNickname.trim().length < 2
				|| inputNickname.trim().length > 6) {
			alert("닉네임은 2~6자리입니다.");
		} else {
			$.ajax({
				url : "/nicknameChk", // 요청서버 url
				type : "post", // 타입
				contentType : "application/json", // 보내는 데이터의 타입
				data : JSON.stringify({"uiNickname" : $("#uiNickname").val()}),// 보낼데이터의 타입
				success : function(data) { // 결과 성공 콜백함수
					if (data.result == 1) {
						alert("중복된 닉네임입니다.");
					} else if (data.result == 0) {
						$('#nicknameChk').val('Y');
						alert("사용가능한 닉네임입니다.");
						$('#nicknameChk').addClass('selected');
					}
				}
			})
		}

	}
	function fn_phoneChk(){
		//문자발송 연결되는 부분 
		$.ajax({
			url : "/sms/joinsend", // 요청서버 url
			type : "post", // 타입
			contentType : "application/json", // 보내는 데이터의 타입
			data : JSON.stringify({"uiPhone" : $("#uiPhone").val()}),// 보낼데이터의 타입
			success : function(data) { // 결과 성공 콜백함수
				if(data.result==true){
					$('#phoneChk').val('Y');
					alert("인증번호가 전송되었습니다.");
					$('#phoneChk').addClass('selected');
				}	
			}
		})
	}
	
	function fn_phonePassChk(){
		//발송해준 번호랑 일치하는지 체크하는부분
		$.ajax({
			url : "/sms/joinsendNumCheck", // 요청서버 url
			type : "post", // 타입
			contentType : "application/json", // 보내는 데이터의 타입
			data : JSON.stringify({"phonePass" : $("#phonePass").val()}),// 보낼데이터의 타입
			success : function(data) { // 결과 성공 콜백함수
				if(data.result==true){
					$('#phonePassChk').val('Y');
					alert("확인되었습니다.");
					$('#phonePassChk').addClass('selected');
				} else {alert("인증번호가 일치하지 않습니다.")}	
			}
		})
		
	}
</script>
</html>