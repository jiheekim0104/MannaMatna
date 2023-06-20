<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- 이거는 잠시 주석처리해놓겠습니당
<%@ include file="/WEB-INF/views/common/common.jsp"%>
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
		alert('${msg}');
		location.href = '${url}';
	</script>
</body>
</html>