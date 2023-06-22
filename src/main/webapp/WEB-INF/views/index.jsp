<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>     
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<!DOCTYPE html>
<link rel="stylesheet" href="${path}/resources/css/index.css" /> 
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="left">
	<img src="../../../resources/upload/logo.png">
</div>
<div class="banner"><img src="../../../resources/upload/404.jpg"></div>
<div class="banner"><img src="../../../resources/upload/405.jpg"></div>
<div class="banner"><img src="../../../resources/upload/500.jpg"></div>
</body>
<script>
//아래에서 위로 올라가는 이미지 슬라이드. 
setInterval( function(){             //setInterval(); 함수 반복 실행 메소드
  $(".banner").delay("2500");        //2.5초 = 2500밀리초 정지
  $(".banner").animate({marginTop:  "-300px" },"500");  //0.5초 동안 위쪽여백:-300px
  $(".banner").delay("2500");                           //2.5초 정지
  $(".banner").animate({marginTop:  "-600px" },"500");  //0.5초 동안 위쪽여백:-600px
  $(".banner").delay("2500");                           //2.5초 정지
  $(".banner").animate({marginTop:  "0" },"500");       //위쪽여백:0px 처음위치로
});

</script>
</html>