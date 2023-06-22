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
		<img src="../../../resources/upload/info1.png" class="info1"><br>
		<br><br><br><br>
		전체 인구의 <span>33.4%</span>에 달하는 <span>1인 가구</span>수,<br>
		맛있는 건 먹고 싶은데, <span>혼자 다 먹긴 부담</span>스럽지 않으신가요?<br>
		<br><br><br><br><br>
	</div>
	<div class="infoBox2">
		<img src="../../../resources/upload/info2.png" class="info2">
		<br><br>
		<span>혹은 혼자 먹으려니까 괜히 입맛이 떨어지지</span> 않나요?<br>
		<sapn>맨날 혼자서 같은거 먹기 질려!</sapn> 누가 맛집좀 알려주면 좋겠다구요?<br>
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
	  		  -	내가 직접 밥상을 만든다.<br>
			  - 밥상생성 버튼을 클릭한다.<br>
			  - 제목, 만나는 날짜, 만나는 시간, 음식 카테고리, 만나는 최대인원수를 설정하고 내용을 입력해주세요.<br>
			  - 인원이 어느정도 모였으면, 마감하기 버튼을 눌러주세요. 더이상 다른 유저가 참여할수없어요. 혹시나 추가 참여인원을 모집하고 싶다면 마감취소버튼을 눌러주세요.<br>
			  - 만남이 이루어지고나면 맛남완료 버튼을 꼭 눌러주세요.</p>
			</div>
	
		<button class="accordion">밥상에는 어떻게 참여할수있나요? (참여자편)</button>
			<div class="panel">
	  			<p>
	  		  - 만들어져있는 밥상에 참여한다.<br>
			  - 원하는 카테고리가있다면 선택할수있어요.<br>
			  - 게시글을 눌러서 밥상내용을 확인해요.<br>
			  - 참여하기 누르면 참여 끝!<br>
			  - 참석이 불가능해진 경우에는 참여취소 버튼을 눌러주요. (단,방장이 인원마감을 한 경우에는 참여취소 버튼을 누를수 없어요.)<br>
			  - 만남 이후에 방장이 '맛남완료'버튼을 누르면 다른 밥상에 참여할수있어요.</p>
			</div>
	
		<button class="accordion">탈퇴는 어떻게하나요?</button>
			<div class="panel">
		 		<p>
		 	- 본인이 방장인데 참여 인원이 없는경우 : 삭제하기 -> 탈퇴<br>
			- 본인이 방장인데 참여 인원이 있는경우 : 마감 -> 만남완료 -> 탈퇴 <br>
			- 밥상에 참여중인경우 : 참여취소 -> 탈퇴<br>
			- 밥상이 인원마감된경우 : 방장이 만남완료를 눌러줄때까지 대기(왜냐면 지가 간다그래놓고 안가는거니까 ^^) -> 탈퇴</p>
			</div>
		<button class="accordion">유저신고는 어떻게하나요?</button>
			<div class="panel">
		 		<p>우측 하단에 있는 챗봇을 통해 관리자와 소통해주세요.<br>
		 		정확한 신고사유 및 근거를 함께 보내주시면 빠른 시일내에 조치하도록 하겠습니다. </p>
		</div>		
	</div>
	<br>
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