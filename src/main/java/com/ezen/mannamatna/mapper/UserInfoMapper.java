package com.ezen.mannamatna.mapper;

import java.util.List;

import com.ezen.mannamatna.vo.UserInfoVO;
import com.ezen.mannamatna.vo.kakaoResult;

public interface UserInfoMapper {
	UserInfoVO selectUserInfo(UserInfoVO userInfoVO);
	
	UserInfoVO selectKakaoUserInfo(kakaoResult kakaoResult); //카카오 로그인 유저
	
	int idChk(UserInfoVO userInfoVO);
	
	int nicknameChk(UserInfoVO userInfoVO);
	
	int insertUserInfo(UserInfoVO userInfoVO);

	int deleteUserInfo(UserInfoVO userInfoVO);

	int updateUserInfo(UserInfoVO userInfoVO);
	
	int updateUiBiNum(UserInfoVO userInfoVO); // 밥상 인서트 후 UserInfoVO에 biNum 업데이트
	
	List<UserInfoVO> selectUserInfos(UserInfoVO userInfoVO); // 회원데이터를 모두 담은 List객체
}
