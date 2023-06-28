package com.ezen.mannamatna.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.ezen.mannamatna.service.SmsService;
import com.ezen.mannamatna.vo.MessageVO;
import com.ezen.mannamatna.vo.SmsResponseVO;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class SmsController {
	
	@Autowired
	SmsService smsService;

	@PostMapping("/sms/send")
	public String sendSms(MessageVO messageVO, Model m, HttpSession session) throws Exception {
		String msg = "전송 실패하였습니다.";
		String url = "/manageUser";
		if(messageVO.getTo()!=null) {
			SmsResponseVO response = smsService.sendSms(messageVO);
			m.addAttribute("response", response);
			session.setAttribute("responseSession", response);
			msg = "전송 성공하였습니다.";
			log.info("전송 후 response confirmNum 확인{}", response);
		}
		m.addAttribute("msg",msg);
		m.addAttribute("url",url);
		return "common/msg";
	}
}
