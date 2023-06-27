package com.ezen.mannamatna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ezen.mannamatna.service.SmsService;
import com.ezen.mannamatna.vo.MessageVO;
import com.ezen.mannamatna.vo.SmsResponseVO;

@Controller
public class SmsController {
	
	@Autowired
	SmsService smsService;
	
	@GetMapping("/send")
	public String getSmsPage() {
		return "user/sendSms";
	}
	
	@PostMapping("/sms/send")
	public String sendSms(MessageVO messageDto, Model model) throws Exception {
		SmsResponseVO response = smsService.sendSms(messageDto);
		model.addAttribute("response", response);
		return "user/result";
	}
}
