<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>     
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<!DOCTYPE html>
<link rel="stylesheet" href="${path}/resources/css/index.css" /> 
<html>
<head>
<meta charset="UTF-8">
<title>만나맛나 : 1인가구를 위한 신개념 밥상 매칭 사이트</title>
</head>
<body>
<div class="left">
	<img src="../../../resources/upload/logo.png">
</div>
<div class="right">
	<div class="image1"><img src="../../../resources/upload/index/slide1.jfif"></div>
	<div class="image2"><img src="../../../resources/upload/index/slide2.jpg"></div>
	<div class="image3"><img src="../../../resources/upload/index/slide3.jpg"></div>
	<div class="image4"><img src="../../../resources/upload/index/slide4.jpg"></div>
	<div class="image5"><img src="../../../resources/upload/index/slide5.jpeg"></div>
</div>
<div class="buttons">
	<button class="mainBnt" onclick="location.href='/main'">메인페이지로 이동하기</button><br>
	<button class="infoBnt" onclick="location.href='/info'">'만나맛나'란?</button>
</div>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
//아래에서 위로 올라가는 이미지 슬라이드. 
setInterval(function(){             //setInterval(); 함수 반복 실행 메소드
  $(".image1").delay("2000");       
  $(".image1").animate({marginTop:  "-929px" },"4000");  
  $(".image2").delay("6000");                          
  $(".image2").animate({marginTop:  "-929px" },"8000");  
  $(".image3").delay("10000");                          
  $(".image3").animate({marginTop:  "-929px" },"12000");  
  $(".image4").delay("14000");                          
  $(".image4").animate({marginTop:  "-929px" },"16000");  
});

setTimeout(function(){    
	document.querySelector('.buttons').style.display="block";},1000);
	
</script>
</html>