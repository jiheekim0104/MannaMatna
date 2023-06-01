package com.ezen.mannamatna.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.mannamatna.controller.UserInfoController;
import com.ezen.mannamatna.mapper.UserInfoMapper;
import com.ezen.mannamatna.vo.UserInfoVO;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Service
public class UserInfoService {
	@Autowired
	private UserInfoMapper uiMapper;
	
	
	public int idChk(UserInfoVO userInfoVO) {
		log.info("여기는서비스=====>{}",userInfoVO);
		return uiMapper.idChk(userInfoVO);
	}
	
	public int nicknameChk(UserInfoVO userInfoVO) {
		// TODO Auto-generated method stub
		return uiMapper.nicknameChk(userInfoVO);
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
