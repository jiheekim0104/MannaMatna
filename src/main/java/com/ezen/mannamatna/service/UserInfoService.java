package com.ezen.mannamatna.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.mannamatna.mapper.UserInfoMapper;
import com.ezen.mannamatna.vo.UserInfoVO;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Service
public class UserInfoService {
	private final String uploadFilePath = "C:\\works\\workspace\\mannamatna\\src\\main\\webapp\\resources\\upload";
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
	
	public boolean join(UserInfoVO userInfoVO) throws IllegalStateException, IOException {
		log.info("userInfoVO====>{}",userInfoVO);
		String fileName = userInfoVO.getUiFile().getOriginalFilename();
		log.info("fileName====>{}",fileName);
		if("".equals(fileName)) {
			userInfoVO.setUiFilepath("/resources/upload/nophoto.png");
		} else if(!"".equals(fileName)) {
			int idx = fileName.lastIndexOf(".");
			String extName = "";
			if (idx != -1) {
				extName = fileName.substring(idx);
			}
			String name = UUID.randomUUID().toString();
			log.info("name====>{}",name);
			File file = new File(uploadFilePath, name+extName);
			userInfoVO.getUiFile().transferTo(file);
			userInfoVO.setUiFilepath("/resources/upload/"+name+extName);
			log.info("저장됨====>{}",userInfoVO);
		}
		return uiMapper.insertUserInfo(userInfoVO)==1;
	}

	public boolean delete(UserInfoVO userInfoVO, HttpSession session){
		return uiMapper.deleteUserInfo(userInfoVO)==1;
	}
	
	public boolean update(UserInfoVO userInfoVO, HttpSession session) throws IllegalStateException, IOException {
		log.info("userInfoVO====>{}",userInfoVO);
		String fileName = userInfoVO.getUiFile().getOriginalFilename();
		log.info("fileName====>{}",fileName);
		if("".equals(fileName)) {
			userInfoVO.setUiFilepath("/resources/upload/nophoto.png");
		} else if(!"".equals(fileName)) {
			int idx = fileName.lastIndexOf(".");
			String extName = "";
			if (idx != -1) {
				extName = fileName.substring(idx);
			}
			String name = UUID.randomUUID().toString();
			log.info("name====>{}",name);
			File file = new File(uploadFilePath, name+extName);
			userInfoVO.getUiFile().transferTo(file);
			userInfoVO.setUiFilepath("/resources/upload/"+name+extName);
			log.info("저장됨====>{}",userInfoVO);
		}
		log.info("서비스/업데이트==>{}",userInfoVO);
		return uiMapper.updateUserInfo(userInfoVO)==1;
	}
}
