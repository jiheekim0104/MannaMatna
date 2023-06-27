<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/sideBar.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>밥상 생성</title>

<!-- timepicker -->
<link rel="stylesheet"
	href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!-- datepicker -->
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<!-- css -->
<link rel="stylesheet" href="${path}/resources/css/babsang-insert.css" />
<style>
.map_wrap, .map_wrap * {margin:0;padding:0;font-family:'Malgun Gothic',dotum,'돋움',sans-serif;font-size:12px;}
.map_wrap a, .map_wrap a:hover, .map_wrap a:active{color:#000;text-decoration: none;}
.map_wrap {position:relative;width:100%;height:500px;}
#menu_wrap {position:absolute;top:0;left:0;bottom:0;width:250px;margin:10px 0 30px 10px;padding:5px;overflow-y:auto;background:rgba(255, 255, 255, 0.7);z-index: 1;font-size:12px;border-radius: 10px;}
.bg_white {background:#fff;}
#menu_wrap hr {display: block; height: 1px;border: 0; border-top: 2px solid #5F5F5F;margin:3px 0;}
#menu_wrap .option{text-align: center;}
#menu_wrap .option p {margin:10px 0;}  
#menu_wrap .option button {margin-left:5px;}
#placesList li {list-style: none;}
#placesList .item {position:relative;border-bottom:1px solid #888;overflow: hidden;cursor: pointer;min-height: 65px;}
#placesList .item span {display: block;margin-top:4px;}
#placesList .item h5, #placesList .item .info {text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
#placesList .item .info{padding:10px 0 10px 55px;}
#placesList .info .gray {color:#8a8a8a;}
#placesList .info .jibun {padding-left:26px;background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png) no-repeat;}
#placesList .info .tel {color:#009900;}
#placesList .item .markerbg {float:left;position:absolute;width:36px; height:37px;margin:10px 0 0 10px;background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png) no-repeat;}
#placesList .item .marker_1 {background-position: 0 -10px;}
#placesList .item .marker_2 {background-position: 0 -56px;}
#placesList .item .marker_3 {background-position: 0 -102px}
#placesList .item .marker_4 {background-position: 0 -148px;}
#placesList .item .marker_5 {background-position: 0 -194px;}
#placesList .item .marker_6 {background-position: 0 -240px;}
#placesList .item .marker_7 {background-position: 0 -286px;}
#placesList .item .marker_8 {background-position: 0 -332px;}
#placesList .item .marker_9 {background-position: 0 -378px;}
#placesList .item .marker_10 {background-position: 0 -423px;}
#placesList .item .marker_11 {background-position: 0 -470px;}
#placesList .item .marker_12 {background-position: 0 -516px;}
#placesList .item .marker_13 {background-position: 0 -562px;}
#placesList .item .marker_14 {background-position: 0 -608px;}
#placesList .item .marker_15 {background-position: 0 -654px;}
#pagination {margin:10px auto;text-align: center;}
#pagination a {display:inline-block;margin-right:10px;}
#pagination .on {font-weight: bold; cursor: default;color:#777;}
</style>
</head>
<body>

	<script
		src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=741c6c8657768cf31100b909d46f72c8&libraries=services"></script>
	<div class="content">
		<form method="POST" action="/addBabsang" style="display: block; width: 100%;" class="form">
			<input type="text" id="biTitle" name="biTitle"
				placeholder="제목을 입력해주세요 (최대 길이 50자)" maxlength="50" required>
			<div class="options">
				<input type="text" id="biMeetingDat" name="biMeetingDat" required>
				<input type="text" id="biMeetingTim" name="biMeetingTim"
					class="timePicker" required> <select id="biFdCategory"
					name="biFdCategory" required>
					<option value="" selected>카테고리 선택</option>
					<option value="한식">한식</option>
					<option value="중식">중식</option>
					<option value="중식">일식</option>
					<option value="양식">양식</option>
					<option value="분식">분식</option>
					<option value="패스트푸드">패스트푸드</option>
					<option value="해산물">해산물</option>
					<option value="족발">족발</option>
				</select> <select id="biHeadCnt" name="biHeadCnt" required>
					<option value="" selected>최대 인원 선택</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
				</select>
			</div>
			<textarea id="biContent" name="biContent"
				placeholder="내용을 입력해주세요 (최대 길이 500자)" maxlength="500"></textarea><br>
			<button id="submit">등록</button>
			<button id="cancle" onclick="location.href='/main'">취소</button>
			
			<br>
			<input type="hidden" name="lat" class="lat" value="0.0">
			<input type="hidden" name="lng" class="lng" value="0.0">
		</form>
		
		<div class="map_wrap">
			<div id="map"
				style="width: 100%; height: 100%; position: relative; overflow: hidden;">
			</div>
			<div id="menu_wrap" class="bg_white">
				<div class="option">
					<div>
						<form onsubmit="searchPlaces(); return false;">
							키워드 : <input type="text" value="" placeholder="장소를 선택하세요!"
								id="keyword" size="15">
							<button type="submit">검색하기</button>
						</form>
					</div>
				</div>
				<hr>
				<ul id="placesList"></ul>
				<div id="pagination"></div>
			</div>
			

		</div>
	</div>
<script>
      
      var markers = [];

      var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
          mapOption = {
              center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
              level: 2 // 지도의 확대 레벨
          };  

      // 지도를 생성합니다    
      var map = new kakao.maps.Map(mapContainer, mapOption); 

      // 장소 검색 객체를 생성합니다
      var ps = new kakao.maps.services.Places();  

      // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
      var infowindow = new kakao.maps.InfoWindow({zIndex:1});

      // 키워드로 장소를 검색합니다
      /* searchPlaces(); */

      // 키워드 검색을 요청하는 함수입니다
      function searchPlaces() {
   
          var keyword = document.getElementById('keyword').value;
          if (!keyword.replace(/^\s+|\s+$/g, '')) {
              alert('키워드를 입력해주세요!');
              return false;
          }

          // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
          ps.keywordSearch(keyword, placesSearchCB); 
      }

      // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
      function placesSearchCB(data, status, pagination) {
          if (status === kakao.maps.services.Status.OK) {

              // 정상적으로 검색이 완료됐으면
              // 검색 목록과 마커를 표출합니다
              displayPlaces(data);

              // 페이지 번호를 표출합니다
              displayPagination(pagination);

          } else if (status === kakao.maps.services.Status.ZERO_RESULT) {

              alert('검색 결과가 존재하지 않습니다.');
              return;

          } else if (status === kakao.maps.services.Status.ERROR) {

              alert('검색 결과 중 오류가 발생했습니다.');
              return;

          }
      }

      // 검색 결과 목록과 마커를 표출하는 함수입니다
      function displayPlaces(places) {

          var listEl = document.getElementById('placesList'), 
          menuEl = document.getElementById('menu_wrap'),
          fragment = document.createDocumentFragment(), 
          bounds = new kakao.maps.LatLngBounds(), 
          listStr = '';
          
   
          removeAllChildNods(listEl);

          // 지도에 표시되고 있는 마커를 제거합니다
          removeMarker(); 
          
          for ( var i=0; i<places.length; i++ ) {

              // 마커를 생성하고 지도에 표시합니다
              var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
                  marker = addMarker(placePosition, i), 
                  itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

              // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
              // LatLngBounds 객체에 좌표를 추가합니다
              bounds.extend(placePosition);

              // 마커와 검색결과 항목에 mouseover 했을때
              // 해당 장소에 인포윈도우에 장소명을 표시합니다
              // mouseout 했을 때는 인포윈도우를 닫습니다
              (function(marker, title) {
                  kakao.maps.event.addListener(marker, 'mouseover', function() {
                      displayInfowindow(marker, title);
                  });

                  kakao.maps.event.addListener(marker, 'mouseout', function() {
                      infowindow.close();
                  });

                  itemEl.onmouseover =  function () {
                      displayInfowindow(marker, title);
                  };

                  itemEl.onmouseout =  function () {
                      infowindow.close();
                  };
              })(marker, places[i].place_name);

              fragment.appendChild(itemEl);
          }

          // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
          listEl.appendChild(fragment);
          menuEl.scrollTop = 0;

          // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
          map.setBounds(bounds);
      }

      // 검색결과 항목을 Element로 반환하는 함수입니다
      function getListItem(index, places) {

          var el = document.createElement('li'),
          itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
                      '<div class="info">' +
                      '   <h5>' + places.place_name + '</h5>';

          if (places.road_address_name) {
              itemStr += '    <span>' + places.road_address_name + '</span>' +
                          '   <span class="jibun gray">' +  places.address_name  + '</span>';
          } else {
              itemStr += '    <span>' +  places.address_name  + '</span>'; 
          }
                       
            itemStr += '  <span class="tel">' + places.phone  + '</span>' +
                      '</div>';           

          el.innerHTML = itemStr;
          el.className = 'item';

          return el;
      }

      // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
      function addMarker(position, idx, title) {
          var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
              imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
              imgOptions =  {
                  spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
                  spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
                  offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
              },
              markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
                  marker = new kakao.maps.Marker({
                  position: position, // 마커의 위치
                  image: markerImage 
              });

          marker.setMap(map); // 지도 위에 마커를 표출합니다
          markers.push(marker);  // 배열에 생성된 마커를 추가합니다
         
          var iwContent = '<div style="padding:5px;">여기에서 맛남!</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
          iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

      // 인포윈도우를 생성합니다
      var infowindow = new kakao.maps.InfoWindow({
          content : iwContent,
          removable : iwRemoveable});
          
      kakao.maps.event.addListener(marker, 'click', function() {
          // 마커 위에 인포윈도우를 표시합니다
            infowindow.open(map, marker);  
      
             // 클릭한 위도, 경도 정보를 가져옵니다 
             var latlng = marker.getPosition();
             console.log(latlng);
             var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
             message += '경도는 ' + latlng.getLng() + ' 입니다';
             var lat = latlng.getLat();
             console.log(lat);
             var lng = latlng.getLng();
             $('.lat').val(lat);
  			 $('.lng').val(lng);
      });
      
      
     
          return marker;
      }

      // 지도 위에 표시되고 있는 마커를 모두 제거합니다
       function removeMarker() {
          for ( var i = 0; i < markers.length; i++ ) {
              markers[i].setMap(null);
          }   
          markers = [];
      } 

      // 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
      function displayPagination(pagination) {
          var paginationEl = document.getElementById('pagination'),
              fragment = document.createDocumentFragment(),
              i; 

          // 기존에 추가된 페이지번호를 삭제합니다
          while (paginationEl.hasChildNodes()) {
              paginationEl.removeChild (paginationEl.lastChild);
          }

          for (i=1; i<=pagination.last; i++) {
              var el = document.createElement('a');
              el.href = "#";
              el.innerHTML = i;

              if (i===pagination.current) {
                  el.className = 'on';
              } else {
                  el.onclick = (function(i) {
                      return function() {
                          pagination.gotoPage(i);
                      }
                  })(i);
              }

              fragment.appendChild(el);
          }
          paginationEl.appendChild(fragment);
      }

      // 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
      // 인포윈도우에 장소명을 표시합니다
      function displayInfowindow(marker, title) {
          var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

          infowindow.setContent(content);
          infowindow.open(map, marker);
      }

       // 검색결과 목록의 자식 Element를 제거하는 함수입니다
      function removeAllChildNods(el) {   
          while (el.hasChildNodes()) {
              el.removeChild (el.lastChild);
          }
      }   
   
   </script>
	</div>
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
		/* let hours = today.getHours();
		let minutes = today.getminutes();
		if(minutes>30){minutes = 30;}
		else{
			minutes = 0;
			hours += 1;
		} */
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

	<script type="text/javascript">
	function hour(now) {
		now;
	}
	</script>
	
</body>
</html>