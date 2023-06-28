<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <h1>테스트문자 보내기</h1>
 
    <form action ="/sms/send" method="post">
        <table>
            <tr class="form-group">
                <td>발송할 전화번호</td>
                <td>
                    <input type="text" class="form-control" name="to" placeholder="전화번호를 입력하세요.">
                </td>
            </tr>
        </table>
        <button class="btn btn-default">발송</button>
    </form>
</body>
</html>