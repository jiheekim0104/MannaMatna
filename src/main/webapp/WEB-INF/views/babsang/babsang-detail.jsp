<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/sideBar.jsp"%>
<%@ include file="/WEB-INF/views/common/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>밥상상세페이지</title>
<link rel="stylesheet" href="${path}/resources/css/babsang-detail.css">
<style>

</style>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=741c6c8657768cf31100b909d46f72c8&libraries=services"></script>
<script>
// 마감된 밥상의 경우 방장의 입장에서만 마감취소 혹은 맛남완료 버튼만 활성화
window.onload = function(){
	let btnList = document.querySelectorAll('.Btn');
	console.log(btnList);
	console.log('콘솔로찍은 biClosed = ' + ${detail.biClosed});
	
	if(${userList.size()==0 && sessionScope.user.uiId != 'administer'}){
		alert('이미 맛남이 완료된 밥상입니다!');
		location.href='/main';
	}
	
	if(${detail.biClosed==true && sessionScope.user.uiId!='administer'}){
		// 해당밥상의 biClosed = true 일때, 마감한 밥상인 경우 버튼
		for(let i = 0;i<btnList.length;i++){
			if(btnList[i].innerText == '마감취소' || btnList[i].innerText == '맛남완료'){
				console.log('버튼텍스트' + btnList[i].innerText);
				continue;
			}
			btnList[i].style.backgroundColor = 'gray';
			btnList[i].style.border = 'gray';
			btnList[i].onclick = null;
			btnList[i].classList.add('ended');
		}
	}
	let commentBoxes = document.querySelectorAll('.commentList');
	console.log('페이지로딩 후 확인 ' + commentBoxes.classList);
}
</script>
</head>
<body>
	<div class="content">
	
		<div class="title">${detail.biTitle}</div>
		<div class="category">${detail.biFdCategory}</div>
		<hr class="line">
		<c:set var="biMeetingTim" value="${detail.biMeetingTim}" />
		<c:set var="biMeetingDat" value="${detail.biMeetingDat}" />
		<div class="time">맛날시간 : ${fn:substring(biMeetingDat,0,4)}.
			${fn:substring(biMeetingDat,5,7)}. ${fn:substring(biMeetingDat,8,10)}
			${fn:substring(biMeetingTim,0,2)}시 ${fn:substring(biMeetingTim,3,5)}분</div>
		<div class="innerContent">${detail.biContent}</div>
		<c:if
			test="${sessionScope.user.uiNum != detail.uiNum && sessionScope.user.biNum != detail.biNum && sessionScope.user.uiId != 'administer'}">
			<%-- 로그인유저와 작성자정보가 다른경우, 및 관리자가 아닐 경우 참가하기 버튼 --%>
			<button class="Btn" type="submit"
				onclick="location.href = '/babsangJoin/${detail.biNum}'">참가하기</button>
		</c:if>
		<c:if
			test="${sessionScope.user.biNum == detail.biNum && sessionScope.user.uiNum != detail.uiNum && sessionScope.user.uiId != 'administer'}">
			<%-- 로그인유저가 밥상번호가 같은 경우(참가중일경우), 및 관리자가 아닐 경우 참가 취소버튼 --%>
			<button class="Btn" type="submit"
				onclick="location.href='/joinCancle/${detail.biNum}'">참가취소</button>
		</c:if>
		<!-- 이 부분 추가했습니다 -->
		<c:if
			test="${sessionScope.user.uiNum == detail.uiNum && detail.biClosed==false && sessionScope.user.uiId != 'administer'}">
			<%-- 세션정보가 작성자이며, 관리자가 아닐 경우 마감하기 하기 전인 경우 마감하기 --%>
			<button class="Btn" type="submit"
				onclick="location.href='/babsangClose/${detail.biNum}'">마감하기</button>
		</c:if>
		<c:if
			test="${sessionScope.user.uiNum == detail.uiNum && sessionScope.user.uiId != 'administer' && detail.biClosed==true}">
			<%-- 세션정보가 작성자이며, 관리자가 아닐 경우 마감상태 후 마감취소버튼 --%>
			<button class="Btn" type="submit"
				onclick="location.href='/babsangCloseCancle/${detail.biNum}'">마감취소</button>
		</c:if>
		<c:if
			test="${sessionScope.user.uiNum == detail.uiNum || sessionScope.user.uiId == 'administer'}">
			<%-- 세션정보가 작성자일 경우, 로그인유저가 작성자 및 관리자 인 경우 밥상삭제 버튼 추가 --%>
			<button class="Btn" id="deleteBtn"
				onclick=<c:if test="${sessionScope.user.uiNum == detail.uiNum}">
			"if(confirm('정말 삭제하시겠습니까?'))location.href='/deleteBabsang?biNum=${detail.biNum}'"
			</c:if>
				<c:if test="${sessionScope.user.uiId =='administer'}">
			"if(confirm('통계 데이터에 변동이 있을 수 있습니다. 정말 삭제하시겠습니까?'))location.href='/deleteBabsang?biNum=${detail.biNum}'"
			</c:if>>밥상삭제</button>
		</c:if>
		<c:if
			test="${sessionScope.user.uiNum == detail.uiNum && ssessionScope.user.uiId != 'administer' && babsangUserList.size()!=0}">
			<%-- 로그인유저가 작성자인 경우, 맛남완료 버튼 추가 --%>
			<button class="Btn"
				onclick="location.href='/successBabsang/${detail.biNum}'">맛남완료</button>
		</c:if>
		<hr>
		<!-- 카카오 지도 삽입 -->
		<!-- 이미지 지도를 표시할 div 입니다 -->
		<div id="staticMap" style="width:100%; height: 400px;"></div>

		<script type="text/javascript"
			src="//dapi.kakao.com/v2/maps/sdk.js?appkey=741c6c8657768cf31100b909d46f72c8"></script>
		<script>

// 이미지 지도에 표시할 마커입니다
var marker = {
    position: new kakao.maps.LatLng(${detail.lat}, ${detail.lng}), 
    text: '여기에서 맛남!' // text 옵션을 설정하면 마커 위에 텍스트를 함께 표시할 수 있습니다
};

var staticMapContainer  = document.getElementById('staticMap'), // 이미지 지도를 표시할 div
    staticMapOption = { 
        center: new kakao.maps.LatLng(${detail.lat}, ${detail.lng}), // 이미지 지도의 중심좌표
        level: 2, // 이미지 지도의 확대 레벨
        marker: marker // 이미지 지도에 표시할 마커
    };

// 이미지 지도를 생성합니다
var staticMap = new kakao.maps.StaticMap(staticMapContainer, staticMapOption);
</script>

		<!-- 참가자정보 영역 -->
		<div class="userImages">
			<hr>
			<div class="participents">참여자정보</div>
			<br>
			<div class="userCount">
				<c:if
					test="${sessionScope.user.uiId != 'administer' || detail.biUserCnt==0}">
			${fn:length(babsangUserList)}(현재인원)
			</c:if>
				<c:if
					test="${sessionScope.user.uiId == 'administer' && detail.biClosed==true && detail.biUserCnt!=0}">
			${detail.biUserCnt}(마감인원)
			</c:if>
				&nbsp;/&nbsp;${detail.biHeadCnt}(최대인원)
			</div>
			<div class="outterBox">
				<div class="box" id="makerBox">

					<img class="profileImg" src="${babsangMaker.uiFilepath}"
						onclick="location.href='/profile/${babsangMaker.uiNum}'">
					<div class="nickName" id="makerNickName">
						<img class="crown" src="../../../resources/upload/왕관.png">
						${babsangMaker.uiNickname}
					</div>

				</div>
			</div>
			<c:forEach items="${babsangUserList}" var="userList">
				<c:if test="${userList.uiNum!=detail.uiNum}">
					<%-- 유저리스트의 uiNum과 해당밥상의 uiNum 이 같지 않을 경우만 실행 --%>
					<%-- 즉, 참가자만 출력 (작성자는 기본 고정) --%>
					<div class="outterBox">
						<div class="box">

							<img class="profileImg" src="${userList.uiFilepath}"
								onclick="location.href='/profile/${userList.uiNum}'">
							<div class="nickName" id="partyNickName">${userList.uiNickname}</div>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</div>
		<div class="container">
			<hr>
			<!-- 댓글영역 -->
			<h3>댓글</h3>
			<div class="commentList"></div>
		</div>
		<!-- 제이쿼리가 제대로 로드 되지 않는 현상 해결 -->

		<!-- 댓글입력 Form -->
		<div class="container">
			<label for="ciContent"></label>
			<form name="commentInsertForm">
				<div class="commentInput">
					<input type="hidden" name="biNum" value="${detail.biNum}" /> <input
						type="text" class="form-control" id="ciContent" name="ciContent"
						placeholder="댓글을 입력하세요."> <span class="input-group-btn">
						<button class="commentBtn" type="button" name="commentInsertBtn"
							onclick="clickEvent()">작성하기</button>
					</span>
				</div>
			</form>
		</div>
		<!-- babsang-comment.jsp 연결 -->
		<%@ include file="babsang-comment.jsp"%>
	</div>
	<!--관리자 채딩 기능-->
	<script type="text/javascript">
var Tawk_API=Tawk_API||{}, Tawk_LoadStart=new Date();
(function(){
var s1=document.createElement("script"),s0=document.getElementsByTagName("script")[0];
s1.async=true;
s1.src='https://embed.tawk.to/6492750694cf5d49dc5eedd0/1h3e1i90i';
s1.charset='UTF-8';
s1.setAttribute('crossorigin','*');
s0.parentNode.insertBefore(s1,s0);
})();
</script>
</body>
</html>