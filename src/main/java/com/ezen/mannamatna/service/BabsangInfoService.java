package com.ezen.mannamatna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.mannamatna.mapper.BabsangInfoMapper;
import com.ezen.mannamatna.vo.BabsangInfoVO;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BabsangInfoService {
	
	@Autowired
	private BabsangInfoMapper babsangInfoMapper;
	
	public List<BabsangInfoVO> getBabsangInfoVOs(BabsangInfoVO babsang){
		return babsangInfoMapper.selectBabsangList(babsang);
	}

	public BabsangInfoVO getBabsangInfoVO(int biNum) {
		// 상세페이지 biNum을 넘겨 받는 방식
		 return babsangInfoMapper.selectBabsangInfo(biNum);
	}
}