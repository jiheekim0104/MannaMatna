<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
// ajax를 이용한 댓글 목록 출력
var biNum = '${detail.biNum}'; // 게시글 번호
console.log(biNum);
	//댓글 목록 
	function commentList(){
	    $.ajax({
	        url : '/comment/list', // 요청url
	        type : 'get', // 요청방식
	        data : {'biNum':biNum},
	        success : function(data){
	        	var a ='';
	        	if(data.length>0){
	        	     $.each(data, function(key, value){ 
	 	                a += '<div class="commentArea">';
	 	                a += '<div class="commentInfo'+value.ciNum+'">'+'댓글번호 : '+value.ciNum+' 작성자 : '+value.uiNum + ' 작성시간 : ' + value.ciCredat + '<br>';
	 	                a += '<div class="commentContent'+value.ciNum+'"> <span> 내용 : '+value.ciContent +'</span>';
	 	                a += '<a onclick="commentUpdate('+value.ciNum+',\''+value.ciContent+'\');"> 수정 </a>';
	 	                a += '<a onclick="commentDelete('+value.ciNum+');"> 삭제 </a> </div>';
	 	                a += '</div></div>';
	 	            });
	        	}else{
	        		a += '<div>';
	                a += '<div><h4><strong>등록된 댓글이 없습니다.</strong></h4>';
	                a += '</div></div>';
	        	}
	            
	            $(".commentList").html(a);
	        }
	    });
	}
	
	$(document).ready(function(){
		commentList(); // 페이지 로딩 시 댓글 목록 출력
	});
</script>