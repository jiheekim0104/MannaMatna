<%@page import="com.ezen.mannamatna.vo.UserInfoVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
UserInfoVO user = (UserInfoVO) session.getAttribute("user");
if (user == null) {
%>
<script>
	alert('로그인이 필요한 서비스입니다. 🍔');
	location.href = '/login';
</script>
<%
return;
}
else if(!user.getUiId().equals("administer")){	
%>
<script>
	alert('관리자 권한이 필요한 서비스입니다. 🍔');
	location.href = '/login';
</script>
<%
return;
}
%>