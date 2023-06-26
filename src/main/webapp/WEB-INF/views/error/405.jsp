<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>        
<!DOCTYPE html>

<html>
<head>
    <title>METHOD NOT ALLOWED</title>
</head>
<%-- <link rel="stylesheet" href="${path}/resources/css/error.css" /> --%>
<style>

img{
    margin: 100px auto 0px auto;
    display: block;
}

div{
	margin: 0px auto;
	text-align: center;
}

.mainBnt {
	width: 200px;
	height: 40px;
	background-color: #FC522F;
	border: 2px solid #FC522F;
	border-radius:0.5em;
	color: #FFFFFF;
	font-weight: bolder;
	padding: 0px 22px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 15px;
	margin-top: 10px;
}

.mainBnt:hover{
	background-color: #FFFFFF;
	color: #FC522F;
}

.backBnt {
	width: 200px;
	height: 40px;
	background-color: #FFFFFF;
	border: 2px solid #FC522F;
	border-radius:0.5em;
	color: #FC522F;
	font-weight: bolder;
	padding: 0px 22px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 15px;
	margin-top: 10px;
}

.backBnt:hover{
	background-color: #FC522F;
	color: #FFFFFF;
}
</style>
<body>
    <img src="/resources/upload/error/405.jpg">
    <div>
    	<button class="mainBnt" onclick="location.href='/main'">메인페이지로 이동</button>
    	<button class="backBnt" onclick="javascript:history.back();">이전페이지로 이동</button>
    </div>
</body>
</html>