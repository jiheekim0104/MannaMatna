<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
	// ajax를 이용한 댓글 목록 출력
	let biNum = '${detail.biNum}'; // 게시글 번호
	console.log(biNum);
	let inputBtn = document.querySelector('.commentBtn');
	console.log(document.getElementById('#ciContent'));
	
	document.addEventListener('keydown', function(event) {
	    if (event.key === 'Enter' && event.target.id === 'ciContent') {
	      event.preventDefault();
	      
	      let content = event.target.value.trim();
	      console.log(event.target.parentNode.classList);
	      if (content === '') {
	        event.target.placeholder = '내용은 필수입니다.';
	        // 부모요소에 애니메이션적용
	        event.target.parentNode.classList.add('vibration');
	        setTimeout(function() {
	        	event.target.parentNode.classList.remove('vibration');
	        }, 400);
	        setTimeout(function() {
	          event.target.placeholder = '내용을 입력하세요.';
	        }, 1000);
	        event.target.focus();
	      } else {
	        let insertData = $('[name=commentInsertForm]').serialize();
	        commentInsert(insertData);
	      }
	    }
	  });
	
	   function clickEvent() { //댓글 등록 버튼 클릭시 
		      
		      console.log('댓글등록눌렀따!!!');
		      let content = commentInsertForm.ciContent;
		      let inputBox = document.querySelector('.commentInput');
		      console.log('댓글내용 : ' + content.value);
		      if(content.value.trim() == ''){
		         // 내용이 없는 없는경우에는 commentInsert 실행하지 않는다.!!
		         content.placeholder='내용은 필수입니다.'; // 인풋박스의 placeholder 변경
		         inputBox.classList.add('vibration'); // 애니메이션 클래스 추가
		         console.log(inputBox);
		         console.log(inputBox.classList);
		         setTimeout(function(){
		            // 0.4초 후 애니메이션 클래스 제거
		            inputBox.classList.remove('vibration');
		         }, 400);
		         setTimeout(function(){
		            // 0.8초 후 placeholder 원상복귀 처리
		            content.placeholder='댓글을 입력하세요.';
		         }, 1000);
		         content.focus(); // 인풋박스 포커스되도록 설정
		      }else{
		         // 내용이 있는 경우에만 인서트 실행
		      let insertData = $('[name=commentInsertForm]').serialize(); //commentInsertForm의 내용을 가져옴
		      commentInsert(insertData); //Insert 함수호출(아래)
		      }
		   };

	//댓글 목록 
	function commentList() {
		$.ajax({
					url : '/comment/list', // 요청url
					type : 'get', // 요청방식
					data : {
						'biNum' : biNum
					},
					success : function(data) {
						let a = '';
						if (data.length > 0) {
							$.each(data,function(key, value) {
											// console.log('세션스코프 : ' + ${sessionScope.user.uiNum});
											a += '<div class = "commentBox">';
											a += '<div class="commentInfo'+value.ciNum+'"><div class = "box"';
											if(value.uiNum == ${babsangMaker.uiNum}){
												// 작성자인경우 img style속성 추가 주황테두리
											a += ' style = "border : 2px solid #FC522F;"';
											}
											a += '><img src="' + value.uiFilepath + '"';
											a += ' class = "profileImg" id = "commentImg" onclick="location.href=\'/profile/' + value.uiNum + '\'"></div>';
											a += '<br><span class = "commentNickName" id = "commentNickName">';
											if(value.uiNum == ${babsangMaker.uiNum}){
												// 로그인유저가 밥상작성자인 경우 닉네임 옆에 왕관이미지추가
											a += '<img class="crown" id = "crown" src="../../../resources/upload/왕관.png">';
											}
											a += value.uiNickname + '</span><span class ="commentTime" id="commentTime">' + value.ciCredat + ' ' + value.ciCretim + '</span><br><br>';
												a += '<div class="commentCiContent'+value.ciNum+'"> <span class="commentContent" id="commentContent">'+ value.ciContent+ '</span>';
												// 로그인시 수정, 삭제기능 가능토록 추가
												if(${sessionScope.user.uiNum}==value.uiNum && ${sessionScope.user.uiNum}!=null){
												// 아래부분 태그들은 세션스코프의 값이 존재하면서, value.uiNum과 값이 같으면 태그가 나오도록 수정
												a += '<div class="commentBtn"><span onclick="commentUpdate('+ value.ciNum+ ',\''+ value.ciContent+ '\');"> 수정 </span>';
												a += '<span onclick="commentDelete('+ value.ciNum+ ');"> 삭제 </span></div>';
												}
												a += '</div>';
												a += '</div></div>';
											});
						} else {
							a += '<div>';
							a += '<div><h4><strong>등록된 댓글이 없습니다.</strong></h4>';
							a += '</div></div>';
						}
						
						$(".commentList").html(a);
					      // Apply animation only to the last comment box
					      let lastCommentBox = $(".commentBox").last();
					      

					      // Check if the last comment box has the "end" class
					      if (!lastCommentBox.hasClass("end")) {
					    	  lastCommentBox.addClass("fadeInUp");
					        setTimeout(function () {
					          // Remove the animation class after 1 second
					          lastCommentBox.removeClass("fadeInUp");
					        }, 1000);
					      }
					}
				});
	}
	// 댓글 작성
	function commentInsert(insertData) {
		$.ajax({
			url : '/comment/insert',
			type : 'post',
			data : insertData,
			success : function(data) {
				if (data == 1) {
					commentList(); //댓글 작성 후 댓글 목록 reload
					$('[name=ciContent]').val('');
					$(".commentBox").last().addClass("end");
				}
			}
		});
	}
	//댓글 수정 - 댓글 내용 출력을 input 폼으로 변경 
	function commentUpdate(ciNum, ciContent){
	    let a ='';
	   
	    a += '<div>';
	    a += '<input class = "form-control" id="commentUpdate" type="text" name="ciContent_'+ciNum+'" value="'+ciContent+'"/>';
	    a += '<div class = "commentBtn"><span onclick="commentUpdateProc('+ciNum+');">수정완료</span>';
	    a += '<span onclick="commentUpdateCancle()">취소</span>';
	    a += '</div></div>';
	    $('.commentCiContent'+ciNum).html(a);
	    let btn = document.getElementById('commentUpdate');
	    btn.focus();
	}
	//댓글 수정
	function commentUpdateProc(ciNum){
	    var updateCiContent = $('[name=ciContent_'+ciNum+']').val();
	    console.log(updateCiContent);
	    $.ajax({
	        url : '/comment/update',
	        type : 'post',
	        data : {'ciContent' : updateCiContent, 'ciNum' : ciNum},
	        success : function(data){
	            if(data == 1) commentList(biNum); //댓글 수정후 목록 출력 
	        }
	    });
	}
	// 댓글 수정 시 취소기능, 다시 리스트를 출력한다.
	function commentUpdateCancle(){
		commentList(); // 기존 댓글 목록 출력
	};
	//댓글 삭제 
	function commentDelete(ciNum){
	    $.ajax({
	        url : '/comment/delete/'+ciNum,
	        type : 'post',
	        success : function(data){
	            if(data == 1) commentList(biNum); //댓글 삭제후 목록 출력 
	            $(".commentBox .commentInfo" + ciNum).addClass("end");
	        }
	    });
	}
	$(document).ready(function() {
		commentList(); // 페이지 로딩 시 댓글 목록 출력
	});
</script>