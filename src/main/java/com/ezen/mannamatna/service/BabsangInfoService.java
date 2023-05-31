package com.ezen.mannamatna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.mannamatna.mapper.BabsangInfoMapper;
import com.ezen.mannamatna.vo.BabsangInfoVO;

@Service
public class BabsangInfoService {
	
	@Autowired
	private BabsangInfoMapper babsangInfoMapper;
	
	public List<BabsangInfoVO> getBabsangInfoVOs(BabsangInfoVO babsangInfo){
		return babsangInfoMapper.selectBabsangList(babsangInfo);
	}
	
}
