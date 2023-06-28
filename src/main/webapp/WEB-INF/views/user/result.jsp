<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div>
  <h2>SMS 내역 조회</h2>        
  <table class="table table-bordered">
    <thead>
      <tr>
        <th>requestId</th>
        <th>요청 시간</th>
        <th>Status Code</th>
        <th>Status Name</th>
        <th>확인 숫자</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>${response.requestId}</td>
        <td>${response.requestTime}</td>
        <td>${response.statusCode}</td>
        <td>${response.statusName}</td>
        <td>${response.smsConfirmNum}</td>
      </tr>
    </tbody>
  </table>
 </div>
</body>
</html>