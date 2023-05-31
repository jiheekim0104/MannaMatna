package com.ezen.mannamatna.mapper;

import java.util.List;

import com.ezen.mannamatna.vo.CommentInfoVO;

public interface CommentInfoMapper {
	// 댓글 목록(각 게시글만의 댓글을 보여준다, 파라미터로 biNum을 받는다.)
	List<CommentInfoVO> selectCommentInfos(int biNum);
	
	// 테스트페이지용
	List<CommentInfoVO> selectCommentInfos(CommentInfoVO commentInfoVO);
	
	// 댓글 작성
	int insertCommentInfo(CommentInfoVO commentInfoVO);
	
	// 댓글 개수
	int countCommentInfo();
	
	// 댓글 수정
	int updateCommentInfo(CommentInfoVO commentInfoVO);
	
	// 댓글 삭제
	int deleteCommentInfo(int ciNum); // 삭제 시 번호만 필요하다.
}