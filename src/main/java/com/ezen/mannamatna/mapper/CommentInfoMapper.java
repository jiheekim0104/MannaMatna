package com.ezen.mannamatna.mapper;

import java.util.List;

import com.ezen.mannamatna.vo.CommentInfoVO;

public interface CommentInfoMapper {
	List<CommentInfoVO> selectCommentInfos(CommentInfoVO commentInfoVO);
}
