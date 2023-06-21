<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/sideBar.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자페이지</title>
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<!-- 제이쿼리가 제대로 로드 되지 않는 현상 해결 -->
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
	//구글 차트 라이브러리 로딩
	google.charts.load('current', {
		'packages' : [ 'corechart' ]
	});
	//로딩이 완료되면 drawChart 함수를 호출
	google.setOnLoadCallback(drawChart); //라이브러리를 불러오는 작업이 완료되었으면 drawChart작업을 실행하라는 뜻.
	function drawChart() {
		var jsonData = $.ajax({ //비동기적 방식으로 호출
			url : '/getChart',
			type : 'get',
			// 컨트롤러로 이동해 맵핑해서 제이슨을 동적으로 직접만들어 그 만든 json을 직접 보낸다.
			// 확장자가 json이면 url 맵핑을 꼭 해주어야 한다. 안해주면 자바파일인줄 알고 404에러가 발생한다.
			// 그렇기 때문에 servlet-context파일에서 리소스를 맵핑해준다.
			dataType : 'json',
			async : false
		}).responseText; //제이슨파일을 text파일로 읽어들인다는 뜻
		console.log(jsonData);
		var jsonData1 = $.ajax({ //비동기적 방식으로 호출
			url : '/getChart1',
			type : 'get',
			// 컨트롤러로 이동해 맵핑해서 제이슨을 동적으로 직접만들어 그 만든 json을 직접 보낸다.
			// 확장자가 json이면 url 맵핑을 꼭 해주어야 한다. 안해주면 자바파일인줄 알고 404에러가 발생한다.
			// 그렇기 때문에 servlet-context파일에서 리소스를 맵핑해준다.
			dataType : 'json',
			async : false
		}).responseText; //제이슨파일을 text파일로 읽어들인다는 뜻
		console.log(jsonData1);
		//데이터테이블 생성
		var data = new google.visualization.DataTable(jsonData);
		var data1 = new google.visualization.DataTable(jsonData1);
		//제이슨 형식을 구글의 테이블 형식으로 바꿔주기 위해서 집어넣음
		var chart = new google.visualization.PieChart(document
				.getElementById('genderChart'));
		var chart1 = new google.visualization.ColumnChart(document
				.getElementById('ageChart'));
		var options = {
			title : '만나맛나 회원 성별 비율',
			width : 600,
			height : 200
		};
		var options1 = {
				title : '만나맛나 회원 연령대 분포도',
				width : 600,
				height : 200,
				animation :{
					startup : true,
					duration: 1000,
					easing: 'out'
				}
		};
		chart.draw(data, options);
		chart1.draw(data1, options1);
	}
</script>
</head>
<style>

#genderChart{
	background-color: red; /* 이건 작업해보시고 지우세용. 튀어나오나 확인해봤음 */
	width : 600px;
	display: inline-block;
}
#ageChart{
	background-color: blue; /* 이건 작업해보시고 지우세용. 튀어나오나 확인해봤음 */
	width : 600px;
	display: inline-block;
}
#joinDateChart{
	background-color: yellow; /* 이건 작업해보시고 지우세용. 튀어나오나 확인해봤음 */
}
</style>
<body>
	<div class="content">
		<h2>통계페이지</h2>
		<hr>
		<h3>가입일(날짜별)동향, 연령대, 성별, count(*) 회원수 가져와야함</h3>
		<div id="genderChart"></div>
		<div id="ageChart"></div>
		<div id="joinDateChart"></div>
		<!-- 차트가 그려지는 영역 -->
		<!-- 차트 새로고침 버튼 -->
		<button id="btn" type="button" onclick="drawChart()">refresh</button>
	</div>
</body>


</html>