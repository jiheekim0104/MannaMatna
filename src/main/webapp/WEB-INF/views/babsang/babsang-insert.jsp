<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="POST" action="/addBabsang">
		<!-- required = 필수요소-->
		<input type="text" id="biTitle" name="biTitle" 
		placeholder="제목" style="width: 50%;" required><br>
			
		<select id="biFdCategory"name="biFdCategory" required>
			<option value="" selected>카테고리 선택</option>
			<option value="한식">한식</option>
			<option value="중식">중식</option>
			<option value="일식">일식</option>
			<option value="양식">양식</option>
			<option value="분식">분식</option>
			<option value="패스트푸드">패스트푸드</option>
			<option value="해산물">해산물</option>
			<option value="족발">족발</option>
		</select>
		
		<input type="date" id="biMeetingDat" name="biMeetingDat" required>
		
		<!-- 나중에 time picker jQuery사용해보기 -->
		<input type="time" id="biMeetingTim" name="biMeetingTim" required>
		
		<select id="biHeadCnt" name="biHeadCnt" required>
			<option value="" selected>최대 인원 선택</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			<option value="6">6</option>
			<option value="7">7</option>
			<option value="8">8</option>
		</select><br> <input type="text" id="biContent" name="biContent"
			placeholder="내용" style="width: 50%; height: 300px"><br>
		<button>등록</button>
	</form>

	<!-- input-날짜-default값 = today -->
	<script>
		document.getElementById('biMeetingDat').valueAsDate = new Date();
	</script>

</body>
</html>