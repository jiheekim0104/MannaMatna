<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/sideBar.jsp"%>
<%@ include file="/WEB-INF/views/common/common.jsp"%>
<%@ include file="/WEB-INF/views/common/admin.jsp"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${path}/resources/css/userList-withdraw.css">
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
	<div class="content">
		<div class="title" id="withdrawTitle"
			onclick="location.href='/manageUser'">탈퇴회원관리</div>
		<div class="title" id="blockTitle"
			onclick="location.href='/blockUser'">정지회원관리</div>
		<br>
		<form action="/blockUser" method="get" id="searchUiNickname" name = "searchUiNickname">
			<input type="text" class="inputUiNickname" name="uiNickname"
				placeholder="찾을 유저의 닉네임을 입력해주세요." value="${param.uiNickname}">
			<button class="Btn">검색</button>
		</form>

		<table class="userTable">
			<tr>
				<th>회원번호</th>
				<th>닉네임</th>
				<th>아이디</th>
				<th>가입날짜</th>
				<th>성별</th>
				<th>연령대</th>
				<th></th>
				<th></th>
			</tr>
			<c:if test="${empty pageUiActive2.list}">
				<tr>
					<td colspan="7">정지된 회원이 없습니다.</td>
				</tr>
			</c:if>
			<c:forEach items="${pageUiActive2.list}" var="userInfoVO">
				<tr>
					<td>${userInfoVO.uiNum}</td>
					<td>${userInfoVO.uiNickname}</td>
					<td>${userInfoVO.uiId}</td>
					<td>${userInfoVO.uiCredat}</td>
					<c:if test="${userInfoVO.uiGender==true}">
						<td>남</td>
					</c:if>
					<c:if test="${userInfoVO.uiGender==false}">
						<td>여</td>
					</c:if>
					<c:if test="${userInfoVO.uiAge==50}">
						<td>${userInfoVO.uiAge}대이상</td>
					</c:if>
					<c:if test="${userInfoVO.uiAge<50}">
						<td>${userInfoVO.uiAge}대</td>
					</c:if>
					<td id="smsConfirmNum"><span>인증번호</span><br> <span
						id="confirmText" class="confirmText${userInfoVO.uiNum}"
						style="font-size: 20px;"></span></td>
					<td>
						<form name="sms" class="sms">
							<input type="hidden" id="uiPhone${userInfoVO.uiNum}" class="uiPhone" name="to"
								value="${userInfoVO.uiPhone}">
							<button class="smsBtn" id="confirmNum" type="button"
								onclick="send(${userInfoVO.uiNum})">인증요청</button>
						</form>
						<button class="Btn"
							onclick="location.href='/blockCancle/${userInfoVO.uiNum}'">정지해제</button>
					</td>
				</tr>
			</c:forEach>
		</table>
		<br>
		<!-- 페이징 -->
		<div id="pageDiv"></div>
		<c:if test="${!(empty pageUiActive2.list)}">
			<script>
				const pages = ${pageUiActive2.pages};//총페이지
				const page = ${pageUiActive2.pageNum};//현재페이지
				const start = Math.floor((page-1)/5)*5+1;//묶음페이지에서 첫페이지
				const end = (start + 4) > pages ? pages : (start + 4);//묶음페이지에서 끝페이지

				let html = '';
			
				if(11<=page){
					html +='<a href="/blockUser?page=1"><<</a>  '
				}//page가 11 이상일때만 맨 처음으로 버튼 보임
				if(start!=1){
					html += '<a href="/blockUser?page=' + (start-1) + '"><</a>'
				}//<버튼 누르면 현재 묶음페이지의 start에서 -1로 (묶음페이지가 첫페이지(1~5)가 아닐 때만 보임)
			
				for(let i=start; i<=end; i++){
					if(i==page){
						html += '<a class="active">' + i + '</a>';
					}else{
						html += ' <a href="/blockUser?page=' + i + '">' + i + '</a>' ;
					}
				}
			
				if(end!=pages){
					html += ' <a href="/blockUser?page=' + (end+1) + '">></a>';
				}//>버튼 누르면 묶음페이지 +1로 (묶음페이지가 첫페이지(1~5)가 아닐 때만 보임)
				if((end/5)<=(Math.ceil(pages/5))-2){
					html +='  <a href="/blockUser?page=' + pages + '">>></a>'
				}//>>버튼 누르면 마지막 묶음페이지로 */
				document.querySelector('#pageDiv').innerHTML = html;
			</script>
		</c:if>
	</div>
	<script>
				let withdrawTitle = document.getElementById('withdrawTitle');
				let blockTitle = document.getElementById('blockTitle');
				withdrawTitle.classList.add('cancle');
	
				// 핸드폰번호 연동 없는 유저는 인증요청버튼 비활성화
				window.onload = function(){
					let smsBtns = document.querySelectorAll('.smsBtn');
					let smsForms = document.querySelectorAll('.sms');
					console.log('폼들의 개수' + smsForms.length);
					console.log(smsForms);
					for(let i = 0;i<smsForms.length;i++){
						// 첫번째 검색폼은 위에서 지워주었다.
						if(smsForms[i].to.value.trim() == ''){
							// 유저정보의 핸드폰번호가 등록되어있지 않으면
							smsBtns[i].style.backgroundColor = 'gray';
							smsBtns[i].style.border = 'gray';
							smsBtns[i].onclick = null;
							smsBtns[i].classList.add('ended');
						}
					}
				}
				function send(uiNum){
					//문자발송 연결되는 부분 
					let number = uiNum;
					let num = $('#uiPhone'+uiNum).val();
					console.log(num);
					let textId = 'confirmText'+number;
					console.log('uiNum' + number);
					console.log('textId' + textId);
					$.ajax({
						url : "/sms/send", // 요청서버 url
						type : "post", // 타입
						contentType : "application/json", // 보내는 데이터의 타입
						data : JSON.stringify({"uiPhone" : $('#uiPhone'+uiNum).val()}),// 보낼데이터의 타입
						success : function(data) { // 결과 성공 콜백함수
							console.log(data);
							console.log(data.result);
							console.log(data.smsConfirmNum);
							if((data.result)=='true'){
								console.log('실행했어????!!!!');
								console.log(textId);
								$("."+textId).text(data.smsConfirmNum);
								alert("인증번호가 전송되었습니다.");
							}	
						}
					});
				}
		</script>
</body>
</html>