package com.ezen.mannamatna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ezen.mannamatna.mapper.UserInfoMapper;
import com.ezen.mannamatna.service.UserInfoService;
import com.ezen.mannamatna.vo.UserInfoVO;

@Controller
public class UserInfoController {
	
	@Autowired
	UserInfoService uiService;
	
	@GetMapping("/") 
	public String home(@ModelAttribute UserInfoVO userInfoVO, Model m) {
		List<UserInfoVO> userList = uiService.selectUserInfo(userInfoVO);
		m.addAttribute("userList", userList);
		return "index"; 
	}
	
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	@PostMapping("/login")
	public String gologin() {
		return "index";
	}
	
	@GetMapping("/join") 
	public String join(Model m) {
		return "user/join"; 
	}
}
