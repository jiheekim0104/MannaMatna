package com.ezen.mannamatna.mapper;

import java.util.List;

import com.ezen.mannamatna.vo.BabsangInfoVO;

public interface BabsangInfoMapper {
	List<BabsangInfoVO> selectBabsangList(BabsangInfoVO babsangInfoVO);
}
