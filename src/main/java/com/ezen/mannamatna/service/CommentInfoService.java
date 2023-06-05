package com.ezen.mannamatna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.mannamatna.mapper.CommentInfoMapper;
import com.ezen.mannamatna.vo.CommentInfoVO;

@Service
public class CommentInfoService {
	
	@Autowired
	CommentInfoMapper commentInfoMapper;
	
	public List<CommentInfoVO> getCommentInfos(int biNum){
		return commentInfoMapper.selectCommentInfos(biNum);
	}
	
//	public List<CommentInfoVO> getCommentInfos(CommentInfoVO commentInfoVO){
//		// 테스트용 
//		return commentInfoMapper.selectCommentInfos(commentInfoVO);
//	}
}
