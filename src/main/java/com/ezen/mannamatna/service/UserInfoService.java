package com.ezen.mannamatna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.mannamatna.mapper.UserInfoMapper;
import com.ezen.mannamatna.vo.UserInfoVO;

@Service
public class UserInfoService {
	@Autowired
	private UserInfoMapper uiMapper;
	
	public List<UserInfoVO> selectUserInfo(UserInfoVO user){
		return uiMapper.selectUserInfo(user);
	}
}
