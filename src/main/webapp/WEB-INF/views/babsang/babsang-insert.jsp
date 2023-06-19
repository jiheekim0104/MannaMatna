<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- timepicker -->
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>

<!-- datepicker -->
<link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>

<body>

<script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>

	<form method="POST" action="/addBabsang">
		<!-- required = 필수요소-->
		<input type="text" id="biTitle" name="biTitle" 
		placeholder="제목" style="width: 50%;" required ><br>
			
		<select id="biFdCategory"name="biFdCategory" required>
			<option value="" selected>카테고리 선택</option>
			<option value="한식">한식</option>
			<option value="중식">중식</option>
			<option value="중식">일식</option>
			<option value="양식">양식</option>
			<option value="분식">분식</option>
			<option value="패스트푸드">패스트푸드</option>
			<option value="해산물">해산물</option>
			<option value="족발">족발</option>
		</select>
		
		<input type="text" id="biMeetingDat" name="biMeetingDat" required>
		
		<input type="text" id="biMeetingTim" name="biMeetingTim" class="timePicker" required>
		
		
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
		<button onclick="location.href='/main'">취소</button>
	</form>

	<!-- datepicker -->
	<script>
		$(function() {
			$( "#biMeetingDat" ).datepicker({
				dateFormat: 'yy-mm-dd',
				showMonthAfterYear:true,
				minDate: 0, //과거 선택 불가
				prevText: '이전 달',
				nextText: '다음 달',
				monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
				monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
				dayNames: ['일', '월', '화', '수', '목', '금', '토'],
				dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
				dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
				changeYear: true,
				changeMonth: true
			});
			/* default = today */
			$( "#biMeetingDat" ).datepicker('setDate', 'today');
			});
		
	</script>
	
	<!-- timepicker -->
	<script>
		/* 30분 간격 */
		$("#biMeetingTim").timepicker({
	    timeFormat: 'H:mm',
	    interval: 30,
	    defaultTime: '18',
	    startTime: '00:00',
	    dynamic: false,
	    dropdown: true,
	    scrollbar: true
		});
		/* default = now */
		/* document.ready(function(){
			$("#biMeetingTim").timepicker('setTime', new Date());
		}); */
	</script>
	
</body>
</html>