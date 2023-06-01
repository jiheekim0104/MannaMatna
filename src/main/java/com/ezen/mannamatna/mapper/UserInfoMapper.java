package com.ezen.mannamatna.mapper;

import com.ezen.mannamatna.vo.UserInfoVO;

public interface UserInfoMapper {
	UserInfoVO selectUserInfo(UserInfoVO userInfoVO);
	
	int idChk(UserInfoVO userInfoVO);
	
	int nicknameChk(UserInfoVO userInfoVO);
	
	int insertUserInfo(UserInfoVO userInfoVO);

	
	
}
