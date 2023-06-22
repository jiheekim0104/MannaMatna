package com.ezen.mannamatna.controller;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.mannamatna.service.BabsangInfoService;
import com.ezen.mannamatna.service.GoogleChartService;
import com.ezen.mannamatna.vo.BabsangInfoVO;
import com.ezen.mannamatna.vo.UserInfoVO;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class AdminController {
	
	@Autowired
	GoogleChartService googleChartService; // 구글 차트서비스 의존 추가
	
	@Autowired
	BabsangInfoService babsangInfoService; // 밥상서비스 의존 추가
	
	@GetMapping("/getPieChart")
	@ResponseBody
	public JSONObject getPieChart(UserInfoVO userInfoVO, HttpSession session) {
		log.info("내가만든 json데이터 확인 ====>{}", googleChartService.getGenderChart(userInfoVO, session));
		return googleChartService.getGenderChart(userInfoVO, session);
	}
	
	@GetMapping("/chart")
	public String goChart(Model m, HttpSession session) {
		m.addAttribute("babsangInfoVO", babsangInfoService.getBabsangInfoCnt(session));
		log.info("biCnt 가 몇이야????{}", babsangInfoService.getBabsangInfoCnt(session));
		return "admin/chart";
	}
	
	@GetMapping("/getColumnChart")
	@ResponseBody
	public JSONObject getColumnChart(UserInfoVO userInfoVO, HttpSession session) {
		log.info("내가만든 json데이터 확인 ====>{}", googleChartService.getAgeChart(userInfoVO, session));
		return googleChartService.getAgeChart(userInfoVO, session);
	}
	@GetMapping("/getLineChart")
	@ResponseBody
	public JSONObject getLineChart(HttpSession session) {
		return googleChartService.getCredatChart(session);
	}
}