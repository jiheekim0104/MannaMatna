package com.ezen.mannamatna.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.mannamatna.service.SmsService;
import com.ezen.mannamatna.vo.MessageVO;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class SmsController {

	@Autowired
	SmsService smsService;

	@PostMapping("/sms/send")
	@ResponseBody
	public Map<String, String> sendSms(@ModelAttribute MessageVO messageVO , @RequestBody Map<String, String> checkMap) throws Exception {
		log.info("포스트 /sms/send 처음 온곳");
		String smsConfirmNum = null;
		String result = "false";
		messageVO.setTo(checkMap.get("uiPhone"));
		log.info("담긴 번호를 messageVO에 넣어줬어{}",messageVO);

		if(messageVO.getTo()!=null) {
			log.info("문자서비스 실행했어?{}",messageVO);
			smsConfirmNum = smsService.sendSms(messageVO).getSmsConfirmNum();
			log.info("문자 확인 번호{}",smsConfirmNum);
			result = "true";
		}
		Map<String, String> map = new HashMap<>();
		map.put("result", result);
		map.put("smsConfirmNum", smsConfirmNum);
		log.info("마지막 리턴 맵 확인{}", map);
		return map;
	}
}
