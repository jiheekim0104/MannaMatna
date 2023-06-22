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
		그런 당신을 위한 신개념 밥상 매칭 사이트!<br>
	</div>
	<div class="image">
		<img src="../../../resources/upload/logo.png"><br>
		‘만나맛나’는 <span>맛집과 사람, 일상을 공유</span>하는<br>
		서비스를 제공하고 있습니다. <br>
	</div>


<br><br>
<span>맛집</span>이라는 공간을 통해 <span>사람</span>과 사람을 연결하고,<br> 
경험을 공유하며, 사용자에게 <span>일상</span>의 행복을 부여합니다.
<br><br>
<hr>
1. 밥상에 참여하는 방법
 (1) 내가 직접 밥상을 만든다.
  - 밥상생성 버튼을 클릭한다.
  - 제목, 만나는 날짜, 만나는 시간, 음식 카테고리, 만나는 최대인원수를 설정하고 내용을 입력해주세요.
  - 인원이 어느정도 모였으면, 마감하기 버튼을 눌러주세요. 더이상 다른 유저가 참여할수없어요. 혹시나 추가 참여인원을 모집하고 싶다면 마감취소버튼을 눌러주세요.
  - 만남이 이루어지고나면 맛남완료 버튼을 꼭 눌러주세요.
  
 (2) 만들어져있는 밥상에 참여한다.
  - 원하는 카테고리가있다면 선택할수있어요.
  - 게시글을 눌러서 밥상내용을 확인해요.
  - 참여하기 누르면 참여 끝!
  - 참석이 불가능해진 경우에는 참여취소 버튼을 눌러주요. (단,방장이 인원마감을 한 경우에는 참여취소 버튼을 누를수 없어요.)
  - 만남 이후에 방장이 '맛남완료'버튼을 누르면 다른 밥상에 참여할수있어요.

2. 탈퇴는 어떻게해요?
3. 유저신고 가능한가요?

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
</script>

</html>