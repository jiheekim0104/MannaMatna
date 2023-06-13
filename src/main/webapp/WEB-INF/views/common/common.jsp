<%@page import="com.ezen.mannamatna.vo.UserInfoVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
UserInfoVO user = (UserInfoVO) session.getAttribute("user");
if (user == null) {
%>
<script>
	alert('밥상을 보려면 로그인을 해주세요 🍔');
	location.href = '/login';
</script>
<%
return;
}
%>