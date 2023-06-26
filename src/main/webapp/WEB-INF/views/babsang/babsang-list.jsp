<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file= "/WEB-INF/views/common/sideBar.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>만나맛나 : 1인가구를 위한 신개념 밥상 매칭 사이트</title>
<link rel="stylesheet" href="${path}/resources/css/babsang-list.css" />

<script type="text/javascript" src=""></script>
</head>

<body>
<div class="content">

	<!-- 제목 검색 기능 -->
	<form action="/main" method="get" id="searchTitle">
		<input type="text" class="inputBiTitle" name="biTitle" placeholder="검색하실 밥상의 제목을 입력해주세요" value="${param.biTitle}">
		<button class="btnBiTitle">검색</button>
	</form>
	<!-- 카테고리 검색 기능 -->
	<form action="/main" method="get" id="searchCategory">
		<button class="btnCategory" name="biFdCategory" value="">전체</button>
		<c:if test="${sessionScope.user.uiId == 'administer'}">
		<button class="btnCategory" name="biClosed" value="1">마감밥상</button>
		<button class="btnCategory" name="biUserCnt" value="1">맛남밥상</button>
		</c:if>
		<button class="btnCategory" name="biFdCategory" value="한식">한식</button>
		<button class="btnCategory" name="biFdCategory" value="중식">중식</button>
		<button class="btnCategory" name="biFdCategory" value="일식">일식</button>
		<button class="btnCategory" name="biFdCategory" value="양식">양식</button>
		<button class="btnCategory" name="biFdCategory" value="분식">분식</button>
		<button class="btnCategory" name="biFdCategory" value="패스트푸드">패스트푸드</button>
		<button class="btnCategory" name="biFdCategory" value="해산물">해산물</button>
		<button class="btnCategory" name="biFdCategory" value="족발">족발</button>
	</form>
	<!-- 밥상 목록 -->
				
	<c:if test="${empty page.list}">
		<div class="empty" >밥상이 존재하지 않습니다. 😇 <div>
	</c:if>
	<c:forEach items="${page.list}" var="babsangListVO">
		<div class="babsang" onmouseenter="zoomIn(event,${babsangListVO.biClosed})" onmouseleave="zoomOut(event,${babsangListVO.biClosed})"
		onclick=
			<c:if test="${babsangListVO.biClosed == false || sessionScope.user.uiId == 'administer'}">
			"location.href='/detail/${babsangListVO.biNum}'"
			</c:if>
			<c:if test="${sessionScope.user.uiNum == null && babsangListVO.biClosed == false}">
			"location.href='/cannotSeeBabsang'"
			</c:if>
			<c:if test="${babsangListVO.biClosed == true && sessionScope.user.biNum == babsangListVO.biNum && babsangListVO.biUserCnt == 0}">
			"location.href='/detail/${babsangListVO.biNum}'"
			</c:if>
			<c:if test="${babsangListVO.biClosed == true && sessionScope.user.biNum != babsangListVO.biNum && babsangListVO.biUserCnt == 0}">
			"alert('인원모집이 종료된 밥상입니다!');" 
			</c:if>
			<c:if test="${babsangListVO.biClosed == true && sessionScope.user.biNum != babsangListVO.biNum && babsangListVO.biUserCnt != 0}">
			"alert('맛남 종료된 밥상입니다!');" 
			</c:if>
			style=
			<c:choose>
				<c:when test="${babsangListVO.biClosed != '0'}">
				"background-image: url('../../../resources/upload/babsang/closedG.png');
				background-size: contain;"
				</c:when>
					<c:otherwise>
					<c:if test="${babsangListVO.biFdCategory == '한식'}">
					"background-image: url('../../../resources/upload/babsang/foodImg/bibimbap.jpg');"
					</c:if>
					<c:if test="${babsangListVO.biFdCategory == '중식'}">
					"background-image: url('../../../resources/upload/babsang/foodImg/blacknoodle.jpg');"
					</c:if>
					<c:if test="${babsangListVO.biFdCategory == '일식'}">
					"background-image: url('../../../resources/upload/babsang/foodImg/sakedong.jpg');"
					</c:if>
					<c:if test="${babsangListVO.biFdCategory == '양식'}">
					"background-image: url('../../../resources/upload/babsang/foodImg/steak.jpg');"
					</c:if>
					<c:if test="${babsangListVO.biFdCategory == '분식'}">
					"background-image: url('../../../resources/upload/babsang/foodImg/ramen.jpg');"
					</c:if>
					<c:if test="${babsangListVO.biFdCategory == '패스트푸드'}">
					"background-image: url('../../../resources/upload/babsang/foodImg/hambuger.jpg');"
					</c:if>
					<c:if test="${babsangListVO.biFdCategory == '해산물'}">
					"background-image: url('../../../resources/upload/babsang/foodImg/seafood.jpg');"
					</c:if>
					<c:if test="${babsangListVO.biFdCategory == '족발'}">
					"background-image: url('../../../resources/upload/babsang/foodImg/pigfoot.jpg');"
					</c:if>
				</c:otherwise>
			</c:choose>
		>
			
		<h3 id="biTitle" style=
			<c:if test="${babsangListVO.biClosed != '0'}">
				"color: black;"
			</c:if>
		>
			${babsangListVO.biTitle}
		</h3>
		
		<div id="biFdCatecory">
			<img src=
				<c:if test="${babsangListVO.biFdCategory == '한식'}">
				"../../../resources/upload/babsang/tag/korfoodtag.jpg"
				</c:if>
				<c:if test="${babsangListVO.biFdCategory == '중식'}">
				"../../../resources/upload/babsang/tag/chifoodtag.jpg"
				</c:if>
				<c:if test="${babsangListVO.biFdCategory == '일식'}">
				"../../../resources/upload/babsang/tag/japfoodtag.jpg"
				</c:if>
				<c:if test="${babsangListVO.biFdCategory == '양식'}">
				"../../../resources/upload/babsang/tag/amefoodtag.jpg"
				</c:if>
				<c:if test="${babsangListVO.biFdCategory == '분식'}">
				"../../../resources/upload/babsang/tag/snackfoodtag.jpg"
				</c:if>
				<c:if test="${babsangListVO.biFdCategory == '패스트푸드'}">
				"../../../resources/upload/babsang/tag/fastfoodtag.jpg"
				</c:if>
				<c:if test="${babsangListVO.biFdCategory == '해산물'}">
				"../../../resources/upload/babsang/tag/seafoodtag.jpg"
				</c:if>
				<c:if test="${babsangListVO.biFdCategory == '족발'}">
				"../../../resources/upload/babsang/tag/pigfoottag.jpg"
				</c:if>
			>
		</div>
			
		<div id="biHeadCnt">
		<c:if test="${babsangListVO.biClosed == false}">
		최대 인원 수 : ${babsangListVO.biHeadCnt}
		</c:if>
		<c:if test="${babsangListVO.biClosed == true && babsangListVO.biUserCnt != 0}">
		마감 인원 수 : ${babsangListVO.biUserCnt}
		</c:if>
		<c:if test="${babsangListVO.biClosed == true && babsangListVO.biUserCnt == 0}">
		인원모집이 종료되었습니다.
		</c:if>
		</div>
		
		<c:set var="biMeetingTim" value="${babsangListVO.biMeetingTim}" />
		<div id="biMeetingDatTim">${babsangListVO.biMeetingDat} / ${fn:substring(biMeetingTim,0,5)}</div>
			
			<%-- 밥상 삭제 버튼--%>
			<%-- <c:if test="${sessionScope.user.uiNum == babsangListVO.uiNum}">
						<button	onclick="location.href='/deleteBabsang?biNum=${babsangListVO.biNum}'">밥상 삭제</button>
				</c:if> --%>
	</div>
	</c:forEach>
	
	<!-- 페이징 -->
	<div id="pageDiv"></div>
		<c:if test="${!(empty page.list)}">
			<script>
				const pages = ${page.pages};//총페이지
				const page = ${page.pageNum};//현재페이지
				const start = Math.floor((page-1)/5)*5+1;//묶음페이지에서 첫페이지
				const end = (start + 4) > pages ? pages : (start + 4);//묶음페이지에서 끝페이지

				let html = '';
			
				if(11<=page){
					html +='<a href="/main?page=1&biTitle=${param.biTitle}&biFdCategory=${param.biFdCategory}"><<</a>  '
				}//page가 11 이상일때만 맨 처음으로 버튼 보임
				if(start!=1){
					html += '<a href="/main?page=' + (start-1) + '&biTitle=${param.biTitle}&biFdCategory=${param.biFdCategory}"><</a>'
				}//<버튼 누르면 현재 묶음페이지의 start에서 -1로 (묶음페이지가 첫페이지(1~5)가 아닐 때만 보임)
			
				for(let i=start; i<=end; i++){
					if(i==page){
						html += '<a class="active">' + i + '</a>';
					}else{
						html += ' <a href="/main?page=' + i + '&biTitle=${param.biTitle}&biFdCategory=${param.biFdCategory}">' + i + '</a>' ;
					}
				}
			
				if(end!=pages){
					html += ' <a href="/main?page=' + (end+1) + '&biTitle=${param.biTitle}&biFdCategory=${param.biFdCategory}">></a>';
				}//>버튼 누르면 묶음페이지 +1로 (묶음페이지가 첫페이지(1~5)가 아닐 때만 보임)
				if((end/5)<=(Math.ceil(pages/5))-2){
					html +='  <a href="/main?page=' + pages + '&biTitle=${param.biTitle}&biFdCategory=${param.biFdCategory}">>></a>'
				}//>>버튼 누르면 마지막 묶음페이지로 */
				document.querySelector('#pageDiv').innerHTML = html;
			</script>
		</c:if>
	</div>	 

	<script type="text/javascript">
		function zoomIn(event,closed) {
 			if(closed){
 				event.target.style.backgroundImage="url('../../../resources/upload/babsang/closedR.png')";
 			}
			event.target.style.transform = "scale(1.05)";
			event.target.style.zIndex = 1;
			event.target.style.transition = "all 0.3s";
			
		}
		function zoomOut(event,closed) {
			if(closed){
				event.target.style.backgroundImage="url('../../../resources/upload/babsang/closedG.png')";
 			}
			event.target.style.transform = "scale(1)";
			event.target.style.zIndex = 0;
			event.target.style.transition = "all 0.3s";
		}
	</script>

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
<!--관리자 채딩 기능-->
</body>
</html>