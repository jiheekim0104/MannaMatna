package com.ezen.mannamatna.mapper;

import java.util.List;

import com.ezen.mannamatna.vo.UserInfoVO;

public interface UserInfoMapper {
	List<UserInfoVO> selectUserInfo(UserInfoVO userInfoVO);
}
