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
	<form action="/main" method="get" id="searchBabsang">
		<input type="text" class="inputBiTitle" name="biTitle" placeholder="검색하실 밥상의 제목을 입력해주세요" value="${param.biTitle}">
		<button class="buttonBiTitle">검색</button>
	</form>
	
	<!-- 카테고리 검색 기능 -->
	<form action="/main" method="get" id="categoryBabsang">
		<button class="biFdCategory" name="biFdCategory" value="">전체</button>
		<button class="biFdCategory" name="biFdCategory" value="한식">한식</button>
		<button class="biFdCategory" name="biFdCategory" value="중식">중식</button>
		<button class="biFdCategory" name="biFdCategory" value="일식">일식</button>
		<button class="biFdCategory" name="biFdCategory" value="양식">양식</button>
		<button class="biFdCategory" name="biFdCategory" value="분식">분식</button>
		<button class="biFdCategory" name="biFdCategory" value="패스트푸드">패스트푸드</button>
		<button class="biFdCategory" name="biFdCategory" value="해산물">해산물</button>
		<button class="biFdCategory" name="biFdCategory" value="족발">족발</button>
	</form>
	
	<!-- 밥상 리스트 목록 -->
				
		<c:if test="${empty page.list}">
			<div>밥상이 존재하지 않습니다</div>
		</c:if>
		<c:forEach items="${page.list}" var="babsangListVO">
		<div class="babsang"
		
			onclick=
			<c:if test="${sessionScope.user.uiNum != null}">
			"location.href='/detail/${babsangListVO.biNum}'"
			</c:if>
			<c:if test="${sessionScope.user.uiNum == null}">
			"location.href='/cannotSeeBabsang'"
			</c:if>
			
			style=
			<c:if test="${babsangListVO.biFdCategory == '중식'}">
			"background-image: url('https://i.namu.wiki/i/IuQwaTmppRSulggeu1XyTm1G_m6viDokh0lN4E_p_gXAnhQgmVOJid25ZqlOfpHS4vvVay9pnP5K0IclP4xv4xekogpfMIFodzj1Unu0btFY5sCSrjYZQf0WOOWs_ryD_9lc_jm6cWIHmxvN-sLNKA.webp');"
			</c:if>
			<c:if test="${babsangListVO.biFdCategory == '양식'}">
			"background-image: url('https://recipe1.ezmember.co.kr/cache/recipe/2022/09/30/8e7eb8e3019532a8dc6d39a9a325aad41.jpg');"
			</c:if>
			<c:if test="${babsangListVO.biFdCategory == '한식'}">
			"background-image: url('https://cdn-bastani.stunning.kr/prod/portfolios/15e92d6a-cfdf-4352-a81d-72f220d4f815/contents/7c74ba433deb488257200574425196e5a34818c7d27eb5362ea94ee18e8a0718_v2.jpg');"
			</c:if>
			
			>
			<h3 id="biTitle">
				제목 : ${babsangListVO.biTitle}
			</h3>
			<hr>
			<div id="biFdCatecory">카테고리 : ${babsangListVO.biFdCategory}</div>
			<hr>
			<div id="biHeadCnt">최대 인원 수 : ${babsangListVO.biHeadCnt}</div>
			<hr>
			<c:set var="biMeetingTim" value="${babsangListVO.biMeetingTim}" />
			<div id="biMeetingDatTim">${babsangListVO.biMeetingDat} / ${fn:substring(biMeetingTim,0,5)}</div>
			
			<%-- 밥상 삭제 버튼--%>
			<%-- <tr>
				<c:if test="${sessionScope.user.uiNum == babsangListVO.uiNum}">
					<td colspan="2" align="center">
						<button
							onclick="location.href='/deleteBabsang?biNum=${babsangListVO.biNum}'">밥상
							삭제</button>
					</td>
				</c:if>
			</tr>  --%>
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