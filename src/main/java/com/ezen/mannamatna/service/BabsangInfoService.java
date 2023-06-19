package com.ezen.mannamatna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.mannamatna.mapper.BabsangInfoMapper;
import com.ezen.mannamatna.vo.BabsangInfoVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class BabsangInfoService {
	
	@Autowired
	private BabsangInfoMapper babsangInfoMapper;
	
	/*기존 밥상 리스트 나열
	 * public List<BabsangInfoVO> getBabsangInfoVOs(BabsangInfoVO babsang){ return
	 * babsangInfoMapper.selectBabsangList(babsang); }
	 */
	
	// 페이징
	public PageInfo<BabsangInfoVO> getPagingBansang(BabsangInfoVO babsang){
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
	public boolean blockJoin(int biNum) {
		// 마감하기 서비스
		BabsangInfoVO babsangInfoVO = babsangInfoMapper.selectBabsangInfo(biNum);
		babsangInfoVO.setBiClosed(true);
		return babsangInfoMapper.updateBiClosed(babsangInfoVO)==1;
	}
	public boolean cancleBlockJoin(int biNum) {
		// 마감취소 서비스
		BabsangInfoVO babsangInfoVO = babsangInfoMapper.selectBabsangInfo(biNum);
		babsangInfoVO.setBiClosed(false);
		return babsangInfoMapper.updateBiClosed(babsangInfoVO)==1;
	}
}