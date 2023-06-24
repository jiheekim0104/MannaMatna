<%@page import="com.ezen.mannamatna.vo.UserInfoVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
UserInfoVO adminChk = (UserInfoVO) session.getAttribute("user");
if(!adminChk.getUiId().equals("administer")){
%>
<script>
alert('관리자 권한이 필요한 서비스입니다. 🍔');
location.href = '/main';
</script>
<%
return;
}
%>