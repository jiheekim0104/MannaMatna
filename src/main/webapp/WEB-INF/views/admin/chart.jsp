<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/sideBar.jsp"%>
<%@ include file="/WEB-INF/views/common/common.jsp"%>
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
		var pieChartJson = $.ajax({ //비동기적 방식으로 호출
			url : '/getPieChart',
			type : 'get',
			// 컨트롤러로 이동해 맵핑해서 제이슨을 동적으로 직접만들어 그 만든 json을 직접 보낸다.
			// 확장자가 json이면 url 맵핑을 꼭 해주어야 한다. 안해주면 자바파일인줄 알고 404에러가 발생한다.
			// 그렇기 때문에 servlet-context파일에서 리소스를 맵핑해준다.
			dataType : 'json',
			async : false
		}).responseText; //제이슨파일을 text파일로 읽어들인다는 뜻
		console.log(pieChartJson);

		var columnChartJson = $.ajax({ //비동기적 방식으로 호출
			url : '/getColumnChart',
			type : 'get',
			// 컨트롤러로 이동해 맵핑해서 제이슨을 동적으로 직접만들어 그 만든 json을 직접 보낸다.
			// 확장자가 json이면 url 맵핑을 꼭 해주어야 한다. 안해주면 자바파일인줄 알고 404에러가 발생한다.
			// 그렇기 때문에 servlet-context파일에서 리소스를 맵핑해준다.
			dataType : 'json',
			async : false
		}).responseText; //제이슨파일을 text파일로 읽어들인다는 뜻
		console.log(columnChartJson);

		var lineChartJson = $.ajax({ //비동기적 방식으로 호출
			url : '/getLineChart',
			type : 'get',
			// 컨트롤러로 이동해 맵핑해서 제이슨을 동적으로 직접만들어 그 만든 json을 직접 보낸다.
			// 확장자가 json이면 url 맵핑을 꼭 해주어야 한다. 안해주면 자바파일인줄 알고 404에러가 발생한다.
			// 그렇기 때문에 servlet-context파일에서 리소스를 맵핑해준다.
			dataType : 'json',
			async : false
		}).responseText; //제이슨파일을 text파일로 읽어들인다는 뜻
		console.log(lineChartJson);

		//데이터테이블 생성
		var pieChartData = new google.visualization.DataTable(pieChartJson);
		var columnChartData = new google.visualization.DataTable(
				columnChartJson);
		var lineChartData = new google.visualization.DataTable(lineChartJson);
		//제이슨 형식을 구글의 테이블 형식으로 바꿔주기 위해서 집어넣음
		var pieChart = new google.visualization.PieChart(document
				.getElementById('genderChart'));
		var columnChart = new google.visualization.ColumnChart(document
				.getElementById('ageChart'));
		var lineChart = new google.visualization.LineChart(document
				.getElementById('credatChart'));

		var pieChartOptions = {
			title : '만나맛나 회원 성별 비율',
			height : 200,
			fontSize: 15,
			fontName : 'omyu_pretty',
			titleTextStyle: {
				fontSize: 18,
			},
			slices: {
				0 : {
					color : '#0d9ca4',
				},
				1 : {
					color : '#FC522F',
				},
			}
		};
		var columnChartOptions = {
			title : '만나맛나 회원 연령대 분포도',
			height : 200,
			fontSize: 15,
			fontName : 'omyu_pretty',
			colors: ['#FC522F'],
			legend:{
				position: 'none',
			},
			titleTextStyle: {
				fontSize: 18,
			},
			animation : {
				startup : true,
				duration : 1500,
				easing : 'in'
			}
		};
		var lineChartOptions = {
			title : '만나맛나 날짜별 가입회원 수 동향',
			height : 300,
			fontName : 'omyu_pretty',
			fontSize: 15,
			colors: ['#FC522F'],
			legend:{
				position: 'none',
			},
			titleTextStyle: {
				fontSize: 18,
			},
			animation : {
				startup : true,
				duration : 1500,
				easing : 'in'
			}
		};
		pieChart.draw(pieChartData, pieChartOptions);
		columnChart.draw(columnChartData, columnChartOptions);
		lineChart.draw(lineChartData, lineChartOptions);
	}
</script>
</head>
<style>
.content .chart {
	margin-top: 50px;
	margin-bottom: 10px;
}
.content .title{
font-size: 35px;
text-align: left;
padding: 20px;
padding-bottom: 10px;
margin-left: 30px;
color: #FC522F;
}

#genderChart {
	width: 49%;
	display: inline-block;
}

#ageChart {
	width: 49%;
	display: inline-block;
}
#credatChart {
	width: 110%;
	display: inline-block;
	float: left;
}

#btn {
	font-weight: bolder;
	font-family: 'omyu_pretty'; /* 버튼 글꼴 */
	width: 115px;
	height: 30px;
	background-color: #FC522F;
	border: 2px solid #FC522F;
	border-radius: 0.5em;
	color: #FFFFFF;
	padding: 2px 10px;
	text-align: center;
	font-size: 15px;
	margin-top: 10px;
	margin-right: 10px;
}

#btn:hover {
	/* .ended 클래스가 있는 버튼은 호버적용 x */
	background-color: #FFFFFF;
	color: #FC522F;
	cursor: pointer;
}
</style>
<body>
	<div class="content">
		<div class = "title">현재 누적 맛남 수 ${babsangInfoVO.biCnt}</div>
		<hr>
		<div class="chart" id="genderChart"></div>
		<div class="chart" id="ageChart"></div>
		<hr>
		<div class="chart" id="credatChart"></div>
		<!-- 차트가 그려지는 영역 -->
		<!-- 차트 새로고침 버튼 -->
		<button id="btn" type="button" onclick="drawChart()">refresh</button>
	</div>
</body>


</html>