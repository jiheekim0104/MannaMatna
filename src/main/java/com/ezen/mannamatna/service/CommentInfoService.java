package com.ezen.mannamatna.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.mannamatna.mapper.CommentInfoMapper;
import com.ezen.mannamatna.mapper.UserInfoMapper;
import com.ezen.mannamatna.vo.CommentInfoVO;
import com.ezen.mannamatna.vo.UserInfoVO;

@Service
public class CommentInfoService {
	
	@Autowired
	CommentInfoMapper commentInfoMapper;
	
	public List<CommentInfoVO> getCommentInfosService(int biNum){
		// 댓글 목록 불러오기
		return commentInfoMapper.selectCommentInfos(biNum);
	}
	
//	public List<CommentInfoVO> getCommentInfos(CommentInfoVO commentInfoVO){
//		// 테스트용 
//		return commentInfoMapper.selectCommentInfos(commentInfoVO);
//	}
	
	public int insertCommentInfoService(CommentInfoVO commetInfoVO) {
		// 댓글 작성 기능
		return commentInfoMapper.insertCommentInfo(commetInfoVO);
	}
	
	public int updateCommentInfoService(CommentInfoVO commentInfoVO) {
		// 댓글 수정 기능
		return commentInfoMapper.updateCommentInfo(commentInfoVO);
	}
	public int deleteCommentInfoService(int ciNum) {
		return commentInfoMapper.deleteCommentInfo(ciNum);
	}
}
