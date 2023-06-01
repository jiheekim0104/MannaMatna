package com.ezen.mannamatna.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
		log.info("=============>{}",uiService.login(userInfoVO, session));
		if(uiService.login(userInfoVO, session)) {
		return "index";
	}
	m.addAttribute("msg","아이디나 비밀번호가 잘못되었습니다.");
	return "user/login";
}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "user/login";
	}
	
	@GetMapping("/join") 
	public String join() {
		return "user/join"; 
	}
	
	@PostMapping("/join-ok")
	public String joinOk(@ModelAttribute UserInfoVO userInfoVO, Model m) {
		if(uiService.join(userInfoVO)) {
			m.addAttribute("msg","회원가입에 성공하셨습니다.");
			return "user/login";
		}
		return "user/join";
	}
	
	@PostMapping("/idChk")
	@ResponseBody
	public Map<String, Integer> idChk(@ModelAttribute UserInfoVO userInfoVO, @RequestBody Map<String, String> checkMap) {
		Map<String, Integer> map = new HashMap<>();
		if(checkMap.get("uiId").trim().equals("")) {
			map.put("result", -1);
			return map; 
		}
		userInfoVO.setUiId(checkMap.get("uiId"));
		log.info("여기는 컨트롤러1===========>{}",userInfoVO.getUiId());
		int result = uiService.idChk(userInfoVO);
		log.info("여기는 컨트롤러2===========>{}",result);
		map.put("result", result);
		return map; 
	}
	
	@PostMapping("/nicknameChk")
	@ResponseBody
	public Map<String, Integer> nicknameChk(@ModelAttribute UserInfoVO userInfoVO, @RequestBody Map<String, String> checkMap) {
		userInfoVO.setUiNickname(checkMap.get("uiNickname"));
		log.info("여기는 컨트롤러1===========>{}",userInfoVO.getUiNickname());
		int result = uiService.nicknameChk(userInfoVO);
		log.info("여기는 컨트롤러2===========>{}",result);
		Map<String, Integer> map = new HashMap<>();
		map.put("result", result);
		return map; 
	}
	
	
	@GetMapping("/profile")
	public String profile() {
		return "user/user-profile";
	}
	
	@GetMapping("/profile-update")
	public String updateProfile() {
		return "user/user-profile-update";
	}
	
	@GetMapping("/withdraw")
	public String withdraw() {
		return "user/user-withdraw";
	}
	
	@GetMapping("/user-check")
	public String check() {
		return "user/user-check";
	}
}
