package com.ezen.mannamatna.mapper;

import java.util.List;	

import com.ezen.mannamatna.vo.BabsangInfoVO;

public interface BabsangInfoMapper {
	
	List<BabsangInfoVO> selectBabsangList(BabsangInfoVO babsang); // 게시물 목록 불러오기
	
	BabsangInfoVO selectBabsangInfo(int biNum); // 게시글 상세

	int insertBabsangInfo(BabsangInfoVO babsang); // 게시물 작성
	
	int deleteBabsangInfo(int biNum); // 게시글 삭제
	
	BabsangInfoVO updateUserInfoBiNum(int uiBiNum); // 유저정보 - biNum
	
	int updateBiClosed(BabsangInfoVO babsangInfoVO); // 마감하기누르면 biClosed 업데이트
}
