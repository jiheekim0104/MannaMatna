package com.ezen.mannamatna.controller;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.mannamatna.service.BabsangInfoService;
import com.ezen.mannamatna.service.GoogleChartService;
import com.ezen.mannamatna.service.UserInfoService;
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

	@Autowired
	UserInfoService userInfoService; // 회원리스트 의존성 추가

	@PostMapping("/getPieChart")
	@ResponseBody
	public JSONObject getPieChart(UserInfoVO userInfoVO, HttpSession session) {
		log.info("내가만든 json데이터 확인 ====>{}", googleChartService.getGenderChart(userInfoVO, session));
		return googleChartService.getGenderChart(userInfoVO, session);
	}

	@GetMapping("/chart")
	public String goChart(Model m, HttpSession session) {
		// 차트페이지로 넘어갈 시 모델에 밥상정보로 맛남이 성사된 밥상객체를 넣어준다.
		m.addAttribute("babsangInfoVO", babsangInfoService.getBabsangInfoCnt(session));
		log.info("biCnt 가 몇이야????{}", babsangInfoService.getBabsangInfoCnt(session));
		return "admin/chart";
	}

	@PostMapping("/getColumnChart")
	@ResponseBody
	public JSONObject getColumnChart(UserInfoVO userInfoVO, HttpSession session) {
		log.info("내가만든 json데이터 확인 ====>{}", googleChartService.getAgeChart(userInfoVO, session));
		return googleChartService.getAgeChart(userInfoVO, session);
	}

	@PostMapping("/getLineChart")
	@ResponseBody
	public JSONObject getLineChart(HttpSession session) {
		return googleChartService.getCredatChart(session);
	}

	// 페이징 기능 추가
	@GetMapping("/manageUser")
	public String goManageUser(UserInfoVO userInfoVO, Model m) {
		// 페이징 객체 모델에 넣어준다.
		// 탈퇴유저 모델에 넣어주고
		m.addAttribute("pageUiActive1", userInfoService.getPagingUiActive1(userInfoVO));
		return "admin/userList-withdraw";
	}

	// 페이징 기능 추가
	@GetMapping("/blockUser")
	public String goBlockUser(UserInfoVO userInfoVO, Model m) {
		// 페이징 객체 모델에 넣어준다.
		// 정지유저 모델에 넣어준다.
		m.addAttribute("pageUiActive2", userInfoService.getPagingUiActive2(userInfoVO));
		return "admin/userList-block";
	}

	@GetMapping("/withdrawCancle/{uiNum}")
	public String withdrawCancle(@PathVariable("uiNum") int uiNum, Model m, HttpSession session) {
		String msg = "비정상적인 접근입니다."; // 유저객체의 정보가 일치하지 않을 경우
		String url = "/main"; // 유저객체의 정보가 일치하지 않을 경우
		UserInfoVO userSession = (UserInfoVO) session.getAttribute("user");
		if (userSession.getUiId().equals("administer")) {
			// 관리자 아이디일 경우만 실행
			UserInfoVO userInfoVO = userInfoService.getUserInfoFromBabsang(uiNum);
			// 유저객체를 찾는 방식이 같아 재사용 (WHERE UI_NUM = #{uiNum or biNum})
			if(userInfoVO.getUiActive()==1 && userInfoVO.getUiDel()!=null) {
				// 탈퇴신청 유저만!
				userInfoVO.setUiDel(null); // 사유 초기화
				userInfoVO.setUiActive(0); // 다시 0으로 초기화
				userInfoService.delete(userInfoVO, session);
				msg = "탈퇴요청 회원 탈퇴해제하였습니다.";
				url = "/manageUser";
			}
		}
		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "common/msg";
	}

	@GetMapping("/blockCancle/{uiNum}")
	public String blockCancle(@PathVariable("uiNum") int uiNum, Model m, HttpSession session) {
		String msg = "비정상적인 접근입니다."; // 유저객체의 정보가 일치하지 않을 경우
		String url = "/main"; // 유저객체의 정보가 일치하지 않을 경우
		UserInfoVO userSession = (UserInfoVO) session.getAttribute("user");
		if (userSession.getUiId().equals("administer")) {
			// 관리자 아이디일 경우만 실행
			UserInfoVO userInfoVO = userInfoService.getUserInfoFromBabsang(uiNum);
			// 유저객체를 찾는 방식이 같아 재사용 (WHERE UI_NUM = #{uiNum or biNum})
			if (userInfoVO.getUiActive() == 2) {
				// 유저객체의 uiActive == 2 정지회원 기준으로만!
				userInfoVO.setUiActive(0); // 다시 0으로 초기화
				userInfoService.updateActive(userInfoVO, session);
				msg = "정지회원 정지 해제하였습니다.";
				url = "/blockUser";
			}
		}
		m.addAttribute("msg", msg);
		m.addAttribute("url", url);
		return "common/msg";
	}

}