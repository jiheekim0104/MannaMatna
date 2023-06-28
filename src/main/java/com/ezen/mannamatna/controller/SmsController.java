package com.ezen.mannamatna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.ezen.mannamatna.service.SmsService;
import com.ezen.mannamatna.vo.MessageVO;
import com.ezen.mannamatna.vo.SmsResponseVO;

@Controller
public class SmsController {
	
	@Autowired
	SmsService smsService;

	@PostMapping("/sms/send")
	public String sendSms(MessageVO messageVO, Model m) throws Exception {
		String msg = "전송 실패하였습니다.";
		String url = "/manageUser";
		if(messageVO.getTo()!=null) {
			SmsResponseVO response = smsService.sendSms(messageVO);
			m.addAttribute("response", response);
			msg = "전송 성공하였습니다.";
		}
		m.addAttribute("msg",msg);
		m.addAttribute("url",url);
		return "common/msg";
	}
	
	
	
}
