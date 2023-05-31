package com.ezen.mannamatna.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ezen.mannamatna.mapper.UserInfoMapper;
import com.ezen.mannamatna.service.UserInfoService;
import com.ezen.mannamatna.vo.UserInfoVO;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class UserInfoController {
	
	@Autowired
	UserInfoService uiService;
	
	@GetMapping("/") 
	public String home(@ModelAttribute UserInfoVO userInfoVO, Model m) {
		return "index"; 
	}
	
	@PostMapping("/") 
	public String home() {
		return "index"; 
	}
	
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	@PostMapping("/login")
	public String gologin(@ModelAttribute UserInfoVO userInfoVO, HttpSession session, Model m) {
		if(uiService.login(userInfoVO, session)) {
		return "index";
	}
	m.addAttribute("msg","아이디나 비밀번호가 잘못되었습니다.");
	return "user/login";
}
	
	@GetMapping("/join") 
	public String join() {
		return "user/join"; 
	}
	
	@PostMapping("/idChk")
	public int idChk(@ModelAttribute UserInfoVO userInfoVO, @RequestBody int result) {
		log.info("===========>{}",userInfoVO.getUiId());
		result = uiService.idChk(userInfoVO);
		log.info("===========>{}",result);
		return result; 
	}
	
	@GetMapping("/profile")
	public String profile(@ModelAttribute UserInfoVO userInfoVO, Model m) {
		m.addAttribute("user", userInfoVO);
		return "user/user-profile";
	}
}
