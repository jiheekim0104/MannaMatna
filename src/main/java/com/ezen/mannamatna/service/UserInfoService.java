package com.ezen.mannamatna.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.mannamatna.mapper.UserInfoMapper;
import com.ezen.mannamatna.vo.UserInfoVO;

@Service
public class UserInfoService {
	@Autowired
	private UserInfoMapper uiMapper;
	
	
	public int idChk(UserInfoVO userInfoVO) {
		return uiMapper.idChk(userInfoVO);
	}

	public boolean login(UserInfoVO userInfoVO, HttpSession session) {
		userInfoVO = uiMapper.selectUserInfo(userInfoVO);
		if(userInfoVO!=null) {
			session.setAttribute("user", userInfoVO);
			return true;
		}
		return false;
	}
	
	public boolean join(UserInfoVO userInfoVO) {
		if(uiMapper.insertUserInfo(userInfoVO)==1) {
			return true;
		}
		return false;
	}
}
