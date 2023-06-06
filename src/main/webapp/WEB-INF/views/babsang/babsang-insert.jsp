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
			<option value="koreanFood">한식</option>
			<option value="chineseFood">중식</option>
			<option value="japaneseFood">일식</option>
			<option value="americanFood">양식</option>
			<option value="bunsik">분식</option>
			<option value="fastFood">패스트푸드</option>
			<option value="seaFood">해산물</option>
			<option value="forkFeet">족발</option>
		</select>
		
		<input type="date" id="biMeetingDat" name="biMeetingDat" required>
		
		<!-- 나중에 time picker jQuery사용해보기 -->
		<input type="time" id="biMeetingTim" name="biMeetingTim" required>
		
		<select id="biHeadCnt" name="biHeadCnt" required>
			<option value="" selected>최대 인원 선택</option>
			<option value="two">2</option>
			<option value="three">3</option>
			<option value="four">4</option>
			<option value="five">5</option>
			<option value="six">6</option>
			<option value="seven">7</option>
			<option value="eight">8</option>
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