package com.ezen.mannamatna.mapper;

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
	
	int updateUiBiNum(UserInfoVO userInfoVO);

}
