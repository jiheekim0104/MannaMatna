<%@page import="org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file= "/WEB-INF/views/common/sideBar.jsp"%>	
<%@ include file="/WEB-INF/views/common/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${path}/resources/css/profile-update.css" />
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js">
</script>
</head>
<body>
<div class="content">
	<form action="/profile-update" method="POST" onsubmit="return checkValue()" enctype="multipart/form-data">
		<div id="imgDiv" class="img">
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
		<div class="info">
			<div class="filebox">
	    		<input class="upload-name" name="uiFilepath" placeholder="프로필 사진" readonly="readonly">
	    		<label for="file" class="labelBnt" onclick="newUpload()">업로드</label> 
	   		 	<input type="file" id="file" name="uiFile" accept="image/*" onchange="loadImg(this)" value="N">
	   		 	<button id="uploadChk" value="N"></button>
			</div>
			<input type="text" name="uiNickname" id="uiNickname" value="${user.uiNickname}" onkeyup="nicknameBntChange()">
			<button class="bnt" type="button" id="nicknameChk" onclick="fn_nicknameChk();" value="N">중복확인</button><br> 	
			<input type="password" class="uiPwd" name="uiPwd" id="uiPwd" placeholder="비밀번호"><br> 
			<input type="password" class="uiPwdCheck" name="uiPwdCheck" id="uiPwdCheck" placeholder="비밀번호 확인"><br>
			<%-- <c:if test="${user.uiPhone==null}"> --%>
				<input type="text" class="uiPhone" name="uiPhone" id="uiPhone" placeholder="휴대전화(-를 제외하고 입력)">
				<button type="button" id="phoneChk" class="bnt" onclick="fn_phoneChk()" value="N" >본인인증</button><br> 
				<input type="text" class="phonePass" name="phonePass" id="phonePass" placeholder="인증번호 입력"> 
				<button type="button" id="phonePassChk" class="bnt" onclick="fn_phonePassChk()" value="N" >인증확인</button><br> 
			<%-- </c:if> --%>
			<select name="uiAge" id="uiAge">
			<option value="0">${user.uiAge}대</option>
				<c:if test="${user.uiAge==10}">
					<option value="10" selected="selected">10대</option>
					<option value="20">20대</option>
					<option value="30">30대</option>
					<option value="40">40대</option>
					<option value="50">50대</option>
				</c:if>	
				<c:if test="${user.uiAge==20}">
					<option value="10">10대</option>
					<option value="20" selected="selected">20대</option>
					<option value="30">30대</option>
					<option value="40">40대</option>
					<option value="50">50대</option>
				</c:if>
				<c:if test="${user.uiAge==30}">
					<option value="10">10대</option>
					<option value="20">20대</option>
					<option value="30" selected="selected">30대</option>
					<option value="40">40대</option>
					<option value="50">50대</option>
				</c:if>
				<c:if test="${user.uiAge==40}">
					<option value="10">10대</option>
					<option value="20">20대</option>
					<option value="30">30대</option>
					<option value="40" selected="selected">40대</option>
					<option value="50">50대</option>
				</c:if>
				<c:if test="${user.uiAge==50}">
					<option value="10">10대</option>
					<option value="20">20대</option>
					<option value="30">30대</option>
					<option value="40">40대</option>
					<option value="50" selected="selected">50대 이상</option>
				</c:if>	

			</select><br>
			<span>성별</span> 
			<c:if test="${user.uiGender==true}">
				<input class="gender" type="radio" name="uiGender" id="uiGender1" value="true" checked="checked"> 남자
				<input class="gender" type="radio" name="uiGender" id="uiGender2" value="false"> 여자<br>
			</c:if>	
			<c:if test="${user.uiGender==false}">
				<input class="gender" type="radio" name="uiGender" id="uiGender1" value="true"> 남자
				<input class="gender" type="radio" name="uiGender" id="uiGender2" value="false" checked="checked"> 여자<br>
			</c:if>	
			
		</div>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		<button class="bnt">수정완료</button>
		<button class="bnt" type="button">취소하기</button>
	</form>
	<script>
/* 	function newUpload(){
		alert('누름!');
		$('#uploadChk').val('Y');
		alert(document.querySelector('#uploadChk').value);
	} */
	function nicknameBntChange(){
		$('#nicknameChk').val('N'); // 중복확인 이후에 다시 닉네임을 바꿨을 경우에 중복확인을 하지않은 상태로 바꿈 
		$('#nicknameChk').removeClass('selected');
	}
		function loadImg(obj) {
			let file =obj.files[0];
			let imgObj = document.querySelector('#imgDiv>img');
			imgObj.src = URL.createObjectURL(file);
			document.querySelector('#imgDiv').style.display='';
			let fileName = $("#file").val().split('/').pop().split('\\').pop();
			$(".upload-name").val(fileName);
		}
		function checkValue(){
/* 			alert(document.querySelector('#uploadChk').value);
			let newUploadChk = document.querySelector('#uploadChk').value;
			if(newUploadChk=='N'){
				$('.upload-name').val == '${user.uiFilepath}';
				alert(document.querySelector('.upload-name').value);
			}
			alert(document.querySelector('.upload-name').value); */
			let inputNickname = document.getElementById('uiNickname').value; 
			let nicknameChk = document.getElementById('nicknameChk').value;
			if(nicknameChk=='N'){
				if(inputNickname=='${user.uiNickname}'){ //닉네임을 수정하지 않은경우에는 중복확인이 이미 처리되었다고 간주(누를필요없음)
					$('#nicknameChk').val('Y');
				} else {
					alert("닉네임 중복확인을 해주세요.");
					return false;
				}
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
			
			if(${user.uiPhone!=null}) {
				$('#phoneChk').val('Y');
				$('#phonePassChk').val('Y');
			}
			
			if(${user.uiPhone==null}){
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
</div>	
</body>
</html>