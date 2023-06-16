package com.ezen.mannamatna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.mannamatna.mapper.BabsangInfoMapper;
import com.ezen.mannamatna.mapper.UserInfoMapper;
import com.ezen.mannamatna.vo.BabsangInfoVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BabsangInfoService {
	
	@Autowired
	private BabsangInfoMapper babsangInfoMapper;
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	public List<BabsangInfoVO> getBabsangInfoVOs(BabsangInfoVO babsang){
		return babsangInfoMapper.selectBabsangList(babsang);
	}
	
	public PageInfo<BabsangInfoVO> getPagingBansang(BabsangInfoVO babsang){
		//페이징 구현중
		PageHelper.startPage(babsang.getPage(), babsang.getRows());
		return new PageInfo<>(babsangInfoMapper.selectBabsangList(babsang));
	}

	public BabsangInfoVO getBabsangInfoVO(int biNum) {
		// 상세페이지 biNum을 넘겨 받는 방식
		 return babsangInfoMapper.selectBabsangInfo(biNum);
	}
	
	public boolean addBabsangInfo(BabsangInfoVO babsang) {
		return babsangInfoMapper.insertBabsangInfo(babsang)==1;
	}
	
	public boolean deleteBabsangInfo(int biNum) {
		return babsangInfoMapper.deleteBabsangInfo(biNum)==1;
	}
	
	public BabsangInfoVO updateUiBiNum(int uiBiNum) {
		return babsangInfoMapper.updateUserInfoBiNum(uiBiNum);
	}
}