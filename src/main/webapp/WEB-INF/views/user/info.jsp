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
<link rel="stylesheet" href="${path}/resources/css/info.css" />
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<div class="content">
	<div class="infoBox1">
		<br><br><br>
		<img src="../../../resources/upload/info/info1.png" class="info1"><br>
		<br><br><br><br>
		전체 인구의 <span>33.4%</span>에 달하는 <span>1인 가구 </span>수,<br>
		맛있는 건 먹고 싶은데, <span>혼자 다 먹긴 부담</span>스럽지 않으신가요? <br>
		<br><br><br><br><br>
	</div>
	<div class="infoBox2">
		<img src="../../../resources/upload/info/info2.png" class="info2">
		<br><br>
		<span>혹은 혼자 먹으려니까 괜히 입맛이 떨어지지</span> 않나요?<br>
		<sapn>맨날 혼자서 같은 거 먹기 질려!</sapn> 누가 맛집 좀 알려주면 좋겠다고요?<br>
		그런 당신을 위한 신개념 밥상 매칭 사이트!<br><br><br><br><br><br><br><br><br>
	</div>
	<div class="image">
		<img src="../../../resources/upload/logo.png"><br>
		‘만나맛나’는 <span>맛집과 사람, 일상을 공유</span>하는<br>
		서비스를 제공하고 있습니다. <br>
	</div>


<br><br>
	<div class="infoBox3">
		<span>맛집</span>이라는 공간을 통해 <span>사람</span>과 사람을 연결하고,<br> 
		경험을 공유하며, 사용자에게 <span>일상</span>의 행복을 부여합니다.
		<br><br>
		<button class="accordion">밥상에는 어떻게 참여할수있나요? (방장편)</button>
			<div class="panel">
	  			<p>
	  			(1) '밥상 생성' 버튼을 클릭합니다.<br>
	  		  <img src="../../../resources/upload/info/1-1.png">
	  		  <br><br>
				(2) 제목, 만나는 날짜, 만나는 시간, 음식 카테고리, 만나는 최대 인원수를 설정하고 내용을 입력해 주세요.<br>
			   <img src="../../../resources/upload/info/1-2.png">
			   <br><br>
				(3) 작성된 글은 메인에서 바로 보실수있습니다. 유저들간의 신뢰높은 약속을위해 수정기능은 구현하지 않았으니 작성된 글을 한번더 확인해주세요.<br>
			   <img src="../../../resources/upload/info/1-3.png">
			   <br><br>
				(4) 인원이 어느 정도 모였으면, '마감하기' 버튼을 눌러주세요. 더 이상 다른 유저가 참여할 수 없어요. <br>혹시나 추가 참여인원을 모집하고 싶다면 '마감취소'버튼을 눌러주세요. <br>
				만남이 이루어지고 나면 '맛남완료' 버튼을 꼭 눌러주세요.<br>
			   <img src="../../../resources/upload/info/1-4.png">
			   <br><br>
				(5) 메인에서 방이 닫혔음을 확인할수있고, 이제 또 다른 밥상 생성과 참여가 가능합니다.
			   <img src="../../../resources/upload/info/1-5.png">
			   <br><br>
				</p>
			</div>
	
		<button class="accordion">밥상에는 어떻게 참여할수있나요? (참여자편)</button>
			<div class="panel">
	  			<p>
	  		  (1) 원하는 밥상을 클릭합니다. 이때 closed가 적혀있는 밥상은 인원이 마감되었거나 맛남이 종료된 밥상이어서 더 이상 참여할 수 없습니다.<br>
	  		  <img src="../../../resources/upload/info/2-1.png">
			  <br><br>
			  (2) 참여하기를 원하시면 '참여하기' 버튼을 눌러주세요.<br>
			  <img src="../../../resources/upload/info/2-2.png">
			  <br><br>
			  (3) 참석이 불가능해진 경우에는 '참여취소' 버튼을 눌러주세요. 단,방장이 인원 마감을 한 경우에는 '참여취소' 버튼을 누를 수 없어요.<br>
			  <img src="../../../resources/upload/info/2-3.png">
			  <br><br>
			  (4) 만남 이후에 방장이 '맛남완료'버튼을 누르면 다른 밥상 생성과 참여가 가능합니다.
			  <img src="../../../resources/upload/info/2-4.png">
			  <br><br>
			  </p>
			</div>
	
		<button class="accordion">탈퇴는 어떻게하나요?</button>
			<div class="panel">
		 		<p>
		 		(1) 프로필에서 '탈퇴하기'버튼을 눌러주세요.<br>
		 		<img src="../../../resources/upload/info/3-1.png">
			  	<br><br>
		 		(2) 탈퇴 사유를 입력하고 비밀번호가 일치하면 탈퇴 처리됩니다.<br>
		 		<img src="../../../resources/upload/info/3-2.png">
			  	<br><br>
		 		(3) 이후에 다시 만나맛나로 돌아오고 싶으시다면 관리자와 소통해 주세요.<br>
			  	<br><br>
		 		<br>
		 		* 참고로 참여 중인 밥상이 있는 경우에는 탈퇴요청을 할 수 없습니다. 아래에 있는 절차를 확인해 주세요.<br>
		 	- 본인이 방장인데 참여 인원이 없는 경우 : 삭제하기 -> 탈퇴<br>
			- 본인이 방장인데 참여 인원이 있는 경우 : 마감 -> 만남완료 -> 탈퇴 <br>
			- 밥상에 참여 중인 경우 : 참여취소 -> 탈퇴<br>
			- 밥상이 참여 중인데 인원 마감된 경우 : 맛남완료 -> 탈퇴</p>
			</div>
		<button class="accordion">유저신고는 어떻게하나요?</button>
			<div class="panel">
		 		<p>우측 하단에 있는 챗봇을 통해 관리자와 소통해 주세요.<br>
		 		정확한 신고 사유 및 근거를 함께 보내주시면 빠른 시일 내에 조치하도록 하겠습니다. </p>
			</div>	
			<br>	
	</div>
</div>	
<!--관리자 채딩 기능-->
<script type="text/javascript">
var Tawk_API=Tawk_API||{}, Tawk_LoadStart=new Date();
(function(){
var s1=document.createElement("script"),s0=document.getElementsByTagName("script")[0];
s1.async=true;
s1.src='https://embed.tawk.to/6492750694cf5d49dc5eedd0/1h3e1i90i';
s1.charset='UTF-8';
s1.setAttribute('crossorigin','*');
s0.parentNode.insertBefore(s1,s0);
})();
</script>
<!--관리자 채딩 기능-->
</body>
<script>
setTimeout(function(){            
	document.querySelector('.infoBox1').style.display="block";},500);
	
setTimeout(function(){            
	document.querySelector('.infoBox2').style.display="block";},2000);	
	
setTimeout(function(){            
	document.querySelector('.image').style.display="block";},3000);	
	
setTimeout(function(){            
	document.querySelector('.infoBox3').style.display="block";},3000);
	
	var acc = document.getElementsByClassName("accordion");
	var i;

	var acc = document.getElementsByClassName("accordion");
	var i;

	for (i = 0; i < acc.length; i++) {
	  acc[i].addEventListener("click", function() {
	    this.classList.toggle("active");
	    var panel = this.nextElementSibling;
	    if (panel.style.maxHeight) {
	      panel.style.maxHeight = null;
	    } else {
	      panel.style.maxHeight = panel.scrollHeight + "px";
	    } 
	  });
	}
</script>

</html>