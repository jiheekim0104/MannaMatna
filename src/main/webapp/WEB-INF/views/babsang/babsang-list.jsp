<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file= "/WEB-INF/views/common/sideBar.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>만나맛나 : 1인가구를 위한 신개념 밥상 매칭 사이트</title>
<link rel="stylesheet" href="${path}/resources/css/babsang-list.css" />

<style type="text/css">

</style>

</head>

<body>
<div class="content">
	<!-- <h1>여기는 만들어진 밥상 리스트</h1> -->
	
	<form action="/main" method="get" >
		<input type="text" class="inputBiTitle" name="biTitle" placeholder="검색하실 밥상의 제목을 입력해주세요" value="${param.biTitle}">
		<button class="buttonBiTitle">검색</button>
	</form>
	<form action="/main" method="get" >
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
	
	<hr>
	<!-- 밥상 리스트 목록 -->
	<table border="1" style="color: black;">
		<c:if test="${empty page.list}"><%-- babsangList --%>
			<th>밥상이 존재하지 않습니다</th>
		</c:if>
		<c:forEach items="${page.list}" var="babsangListVO"><!-- babsangList -->
			<tr>
				<th colspan="2">
					<%-- 로그인 시 밥상을 구경할 수 있게--%>
					<c:if test="${sessionScope.user.uiId != null}">
						<a href="/detail/${babsangListVO.biNum}">제목 : ${babsangListVO.biTitle}</a>
					</c:if>
					<%-- 비로그인 시 밥상을 구경할 수 없게--%>
					<c:if test="${sessionScope.user.uiId == null}">
						<a href="/cannotSeeBabsang">제목 : ${babsangListVO.biTitle}</a>
					</c:if>
					
				</th>
			</tr>
<!-- 생성자, 보드 번호 -->
			<%-- <tr>
				<td colspan="2">밥상 번호 : ${babsangListVO.biNum}
				</td>
			</tr>
			<tr>
				<td colspan="2">밥상 생성자 : ${babsangListVO.uiNum}
				</td>
			</tr> --%>
<!-- 이 까지 -->
			<tr>
				<td colspan="2">카테고리 : ${babsangListVO.biFdCategory}</td>
			</tr>
			<tr>
				<td colspan="2">최대 인원 수 : ${babsangListVO.biHeadCnt}</td>
			</tr>
			<tr>
				<td>날짜 : ${babsangListVO.biMeetingDat}</td>
				<td>시간 : ${babsangListVO.biMeetingTim}</td>
			</tr>
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
		</c:forEach>
	</table>
	
	 <!-- 페이징 -->
	<div id="pageDiv" style="text-align:center; color:aqua; font-size:14pt"></div>
	<c:if test="${!(empty page.list)}">
		<script>
			const pages = ${page.pages};//총페이지
			const page = ${page.pageNum};//현재페이지
			const start = Math.floor((page-1)/5)*5+1;//묶음페이지에서 첫페이지
			const end = (start + 4) > pages ? pages : (start + 4);//묶음페이지에서 끝페이지

			let html = '';
			
			if(11<=page){
				html +='<a href="/main?page=1&biTitle=${param.biTitle}&biFdCategory=${param.biFdCategory}">&#x219E</a>  '
			}//page가 11 이상일때만 맨 처음으로 버튼 보임
			if(start!=1){
				html += '<a href="/main?page=' + (start-1) + '&biTitle=${param.biTitle}&biFdCategory=${param.biFdCategory}">&#x2190</a>'
			}//<버튼 누르면 현재 묶음페이지의 start에서 -1로 (묶음페이지가 첫페이지(1~5)가 아닐 때만 보임)
			
			for(let i=start; i<=end; i++){
				if(i==page){
					html += ' [' + i + '] ';
				}else{
					html += ' <a href="/main?page=' + i + '&biTitle=${param.biTitle}&biFdCategory=${param.biFdCategory}">[' + i + ']</a>' ;
				}
			}
			
			if(end!=pages){
				html += ' <a href="/main?page=' + (end+1) + '&biTitle=${param.biTitle}&biFdCategory=${param.biFdCategory}">&#x2192</a>';
				
			}//>버튼 누르면 묶음페이지 +1로 (묶음페이지가 첫페이지(1~5)가 아닐 때만 보임)
			
			if((end/5)<=(Math.ceil(pages/5))-2){
				html +='  <a href="/main?page=' + pages + '&biTitle=${param.biTitle}&biFdCategory=${param.biFdCategory}">&#x21A0</a>'
				}//>>버튼 누르면 마지막 묶음페이지로 */
				
			document.querySelector('#pageDiv').innerHTML = html;
		</script>
	</c:if>
</div>	 


</body>
</html>