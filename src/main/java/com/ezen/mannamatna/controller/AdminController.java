package com.ezen.mannamatna.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezen.mannamatna.service.GoogleChartService;
import com.ezen.mannamatna.vo.UserInfoVO;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class AdminController {
	
	@Autowired
	GoogleChartService googleChartService; // 구글 차트서비스 의존 추가
	
	@GetMapping("/chart")
	public String goChart(UserInfoVO userInfoVO, HttpSession session) {
		googleChartService.getChartData(userInfoVO, session); // 테스트용 서비스 실행
		log.info("내가만든 json데이터 확인 ====>{}", googleChartService.getChartData(userInfoVO, session));
		return "/admin/chart";
	}
}