package com.ezen.mannamatna.controller;

import org.springframework.beans.factory.annotation.Autowired;	
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezen.mannamatna.service.BabsangInfoService;

@Controller
public class BabsangInfoController {
	
	@Autowired
	private BabsangInfoService babsangInfoService;
	
	@GetMapping("/main")
	public String goMain() {
		return "/babsang/babsang-list";
	}
}