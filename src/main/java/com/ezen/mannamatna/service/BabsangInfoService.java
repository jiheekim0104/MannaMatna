package com.ezen.mannamatna.service;

import javax.servlet.http.HttpSession;

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

	/*
	 * 기존 밥상 리스트 나열 public List<BabsangInfoVO> getBabsangInfoVOs(BabsangInfoVO
	 * babsang){ return babsangInfoMapper.selectBabsangList(babsang); }
	 */

	// 페이징
	public PageInfo<BabsangInfoVO> getPagingBansang(BabsangInfoVO babsang) {
		PageHelper.startPage(babsang.getPage(), babsang.getRows());
		return new PageInfo<>(babsangInfoMapper.selectBabsangList(babsang));
	}

	public BabsangInfoVO getBabsangInfoVO(int biNum) {
		// 상세페이지 biNum을 넘겨 받는 방식
		return babsangInfoMapper.selectBabsangInfo(biNum);
	}

	/* 밥상 생성 */
	public boolean addBabsangInfo(BabsangInfoVO babsang) {
		return babsangInfoMapper.insertBabsangInfo(babsang) == 1;
	}

	/* 밥상 삭제 */
	public boolean deleteBabsangInfo(int biNum) {
		return babsangInfoMapper.deleteBabsangInfo(biNum) == 1;
	}

	/* 밥상 생성 시 autoIncrement될 BiNum을 받음 */
	public BabsangInfoVO updateUiBiNum(int uiBiNum) {
		return babsangInfoMapper.updateUserInfoBiNum(uiBiNum);
	}

	public boolean blockJoin(int biNum) {
		// 마감하기 서비스
		BabsangInfoVO babsangInfoVO = babsangInfoMapper.selectBabsangInfo(biNum);
		babsangInfoVO.setBiClosed(true); // VO에 값을 해당 서비스에서 SET 후 MAPPER에 보낸다.
		return babsangInfoMapper.updateBiClosed(babsangInfoVO) == 1;
	}

	public boolean cancleBlockJoin(int biNum) {
		// 마감취소 서비스
		BabsangInfoVO babsangInfoVO = babsangInfoMapper.selectBabsangInfo(biNum);
		babsangInfoVO.setBiClosed(false); // VO에 값을 해당 서비스에서 SET 후 MAPPER에 보낸다.
		return babsangInfoMapper.updateBiClosed(babsangInfoVO) == 1;
	}

	public boolean meetingSuccess(BabsangInfoVO babsangInfoVO) {
		// 밥상 맛남 완료 후 맛남 유저 수 업데이트
		return babsangInfoMapper.updateBiUserCnt(babsangInfoVO) == 1;
	}
	public BabsangInfoVO getBabsangInfoCnt(HttpSession session) {
		// 맛남 성사된 밥상 리턴 받는 서비스
		return babsangInfoMapper.selectBiCnt();
	}
}