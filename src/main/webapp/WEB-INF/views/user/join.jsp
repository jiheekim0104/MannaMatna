<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
	하위 여기서 가입하면됨이야. <br>
	SNS 연동<br>
	<form action="/" method="POST">
		<input type="text" name="uiId" id="uiId" placeholder="아이디">
		<button type="button" id="idChk" onclick="fn_idChk();" value="N">중복확인</button>
		<br>
		<input type="text" name="uiNickname" id="uiNickname" placeholder="닉네임">
		<button>중복확인</button>
		<br>
		<input type="password" name="uiPwd" id="uiPwd" placeholder="비밀번호"><br>
		<input type="password" name="uiPwdCheck" id="uiPwdCheck" placeholder="비밀번호 확인"><br> 
		<input type="text" name="uiPhoto" id="uiPhoto" placeholder="프로필 사진">
		<button>업로드</button>
		<br> 
		<select name="uiAge" id="uiAge">
			<option value="none">연령대를 선택하세요.</option>
			<option value="10">10대</option>
			<option value="20">20대</option>
			<option value="30">30대</option>
			<option value="40">40대</option>
			<option value="50">50대 이상</option>
		</select> 
		<input type="radio" name="uiGender" id="uiGender1" value="1">남자
		<input type="radio" name="uiGender" id="uiGender2" value="2">여자
		<button>가입완료</button>
	</form>


</body>
<script>
function fn_idChk(){
	$.ajax({
		url : "/idChk",
		type : "post",
		contentType: "application/json",
		dataType : "json",
		data : JSON.stringify({"uiId" : $("#uiId").val()}),
		success : function(data){
			if(data == 1){
				alert("중복된 아이디입니다.");
			}else if(data == 0){
				$("#idChk").attr("value", "Y");
				alert("사용가능한 아이디입니다.");
			}
		}
	})
}
</script>
</html>