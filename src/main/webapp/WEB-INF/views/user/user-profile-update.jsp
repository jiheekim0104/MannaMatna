<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
	<h1>프로필 수정</h1>
	${user}
	<form action="/profile-update" method="POST" onsubmit="return checkValue()" enctype="multipart/form-data">
	<c:if test="${user.uiFilepath!=null}">
			<img id="imgDiv" src="${user.uiFilepath}" width="300">
			<input type="file" name="uiFile" id="uiFile" onchange="loadImg(this)">
			<br>
		</c:if>
		<input type="text" name="uiNickname" id="uiNickname"
			value="${user.uiNickname}">
		<button type="button" id="nicknameChk" onclick="fn_nicknameChk();"
			value="N">중복확인</button>
		<br> <input type="password" name="uiPwd" id="uiPwd"
			value="${user.uiPwd}"><br> <input type="password"
			name="uiPwdCheck" id="uiPwdCheck" placeholder="비밀번호 확인"><br>
		
		성별 : <a id="uiGender" name="uiGender"></a><br>
연령대 : ${user.uiAge}대<br>
		<button>확인</button>
	</form>
	<script>
	if(${user.uiGender}){
		document.querySelector('#uiGender').innerHTML = '남자';
	} else {
		document.querySelector('#uiGender').innerHTML = '여자';
	}
		function loadImg(obj) {
			let file = obj.files[0];
			let imgObj = document.querySelector('#imgDiv');
			imgObj.src = URL.createObjectURL(file);
			document.querySelector('#imgDiv').style.display = '';
		}
		function checkValue(){
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
						}
					}
				})
			}

		}
	</script>
</body>
</html>