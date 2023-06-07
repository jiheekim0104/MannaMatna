<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	// ajax를 이용한 댓글 목록 출력
	var biNum = '${detail.biNum}'; // 게시글 번호
	console.log(biNum);

	$('[name=commentInsertBtn]').click(function() { //댓글 등록 버튼 클릭시 
		console.log('댓글등록눌렀따!!!');
		var insertData = $('[name=commentInsertForm]').serialize(); //commentInsertForm의 내용을 가져옴
		commentInsert(insertData); //Insert 함수호출(아래)
	});

	//댓글 목록 
	function commentList() {
		$.ajax({
					url : '/comment/list', // 요청url
					type : 'get', // 요청방식
					data : {
						'biNum' : biNum
					},
					success : function(data) {
						var a = '';
						if (data.length > 0) {
							$.each(data,function(key, value) {
											a += '<div style="border-bottom:1px solid darkgray; margin-bottom: 15px;">';
											a += '<div class="commentInfo'+value.ciNum+'">'+ '댓글번호 : '+value.ciNum+'<img src="' + value.uiFilepath + '" width="50">'+ ' 작성자 : '+ value.uiNickname+ ' 작성시간 : '+ value.ciCredat + '<br>';
												a += '<div class="commentCiContent'+value.ciNum+'"> <p> 내용 : '+ value.ciContent+ '</p>';
												a += '<a onclick="commentUpdate('+ value.ciNum+ ',\''+ value.ciContent+ '\');"> 수정 </a>';
												a += '<a onclick="commentDelete('+ value.ciNum+ ');"> 삭제 </a> </div>';
												a += '</div></div>';
											});
						} else {
							a += '<div>';
							a += '<div><h4><strong>등록된 댓글이 없습니다.</strong></h4>';
							a += '</div></div>';
						}

						$(".commentList").html(a);
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
				}
			}
		});
	}
	//댓글 수정 - 댓글 내용 출력을 input 폼으로 변경 
	function commentUpdate(ciNum, ciContent){
	    var a ='';
	    
	    a += '<div>';
	    a += '<input type="text" name="ciContent_'+ciNum+'" value="'+ciContent+'"/>';
	    a += '<span><button type="button" onclick="commentUpdateProc('+ciNum+');">수정</button></span>';
	    a += '</div>';
	    
	    $('.commentCiContent'+ciNum).html(a);
	    
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
	
	$(document).ready(function() {
		commentList(); // 페이지 로딩 시 댓글 목록 출력
	});
	
</script>