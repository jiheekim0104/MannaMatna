package com.ezen.mannamatna.mapper;

import java.util.List;

import com.ezen.mannamatna.vo.BabsangInfoVO;

public interface BabsangInfoMapper {
	List<BabsangInfoVO> selectBabsangList(BabsangInfoVO babsang);

	BabsangInfoVO selectBabsangInfo(int biNum); // 게시글 상세
}
