<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
	<h1>회원가입</h1>
	SNS 연동<br>
	<form action="/join-ok" method="POST" onsubmit="return checkValue()" enctype="multipart/form-data">
		<input type="text" name="uiId" id="uiId" placeholder="아이디">
		<button type="button" id="idChk" onclick="fn_idChk();" value="N">중복확인</button><br> 
		<input type="text" name="uiNickname" id="uiNickname" placeholder="닉네임">
		<button type="button" id="nicknameChk" onclick="fn_nicknameChk();" value="N">중복확인</button><br> 
		<input type="password" name="uiPwd" id="uiPwd" placeholder="비밀번호"><br> 
		<input type="password" name="uiPwdCheck" id="uiPwdCheck" placeholder="비밀번호 확인"><br>
		<input type="file" name="uiFile" id="uiFile" onchange="loadImg(this)"><br> 
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
		<input type="radio" name="uiGender" id="uiGender1" value="true">남자
		<input type="radio" name="uiGender" id="uiGender2" value="false">여자
		<button type="button" onclick="show();" value="N">여기</button><br>
		<button>가입완료</button>
	</form>


</body>
<script>
	let inputGender = document.querySelector([name='uiGender']); // 입력받은 성별 미선택 처리 필요함
	let inputGender1 = document.getElementById('uiGender1').value;
	let inputGender2 = document.getElementById('uiGender2').value;
	function show(){
		alert(!(inputGender1==true || inputGender2==false));
	}
	function loadImg(obj){
		let file =obj.files[0];
		let imgObj = document.querySelector('#imgDiv>img');
		imgObj.src = URL.createObjectURL(file);
		document.querySelector('#imgDiv').style.display='';
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
		
		let inputAge = document.getElementById('uiAge').value; // 입력받은 연령대
		if(inputAge == 0){  
			alert("연령대를 선택해주세요.");
			return false;
		}
		
		
		if(!(inputGender1==true || inputGender2==false)){  
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
					} else if (data.result == -1) {
						alert("아이디를 입력하세요.");
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
					}
				}
			})
		}

	}
</script>
</html>