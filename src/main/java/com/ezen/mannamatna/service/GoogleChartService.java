package com.ezen.mannamatna.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.mannamatna.vo.UserInfoVO;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class GoogleChartService {

	@Autowired
	UserInfoService userInfoService; // 유저인포서비스 의존주입

	public JSONObject getChartData(UserInfoVO userInfoVO, HttpSession session) {
		List<UserInfoVO> items = userInfoService.getUserInfos(userInfoVO, session);
		log.info("구글차트서비스에서 의존주입받은 유저서비스 메소드실행 후 유저리스트==>{}", items); // 유저서비스 메소드 정상실행 확인
		JSONObject data = new JSONObject(); // 해당 서비스에서 jsonObject를 컨트롤러에 리턴한다.
		return data;
	}
}
