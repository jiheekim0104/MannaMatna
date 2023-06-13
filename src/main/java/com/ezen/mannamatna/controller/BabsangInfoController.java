package com.ezen.mannamatna.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.mannamatna.service.BabsangInfoService;
import com.ezen.mannamatna.service.UserInfoService;
import com.ezen.mannamatna.vo.BabsangInfoVO;
import com.ezen.mannamatna.vo.UserInfoVO;
import com.mysql.cj.Session;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class BabsangInfoController {
	
	@Autowired
	private BabsangInfoService babsangInfoService;
	@Autowired 
	private UserInfoService userInfoService;
	 
	
	@GetMapping("/main")
	public String goMain(BabsangInfoVO babsang, Model m){
		List<BabsangInfoVO> babsangList = babsangInfoService.getBabsangInfoVOs(babsang);
		m.addAttribute("babsangList", babsangList);
		return "babsang/babsang-list";
	}
	
	@GetMapping("/addBabsang")
	public String goCreateBabsang(){
		return "babsang/babsang-insert";
	}
	
	@PostMapping("/addBabsang")
	public String insertBabsang(BabsangInfoVO babsang,  UserInfoVO userInfoVO,  Model m, HttpSession session) {
		UserInfoVO user = (UserInfoVO)session.getAttribute("user");

		babsang.setUiNum(user.getUiNum()); //밥상의 uiNum을 로그인한 유저의 uiNum으로 셋
		log.info("userInfo_biNum ===> {}", babsang.getUiBiNum()); // = 0 아직 안받아오는 상태
		String msg = "밥상 등록 실패";
		String url = "/babsang-insert";
		
		if(babsangInfoService.addBabsangInfo(babsang)) {
			int biNum = babsang.getUiBiNum(); //인서트 할 때의 autoIncrement 된 밥상 번호를 받아옴
			log.info("uiBiNum ===> {}", biNum); // = 이제서야 받아옴
			userInfoVO.setBiNum(biNum); // 유저의 biNum을 받아온 biNum으로 셋
			userInfoVO.setUiNum(user.getUiNum());
			user.setBiNum(biNum); //세션의 biNum을 받아온 biNum으로 셋 
			log.info("userInfo.biNum ===> {}", userInfoVO.getBiNum());
			msg = "밥상 등록 성공";
			url = "/main";
			userInfoService.updateBiNum(userInfoVO); // 인서트 성공 시 유저서비스의 update 실행
			log.info("인서트 성공 후 유저인포의 biNum =====> {}", userInfoVO.getBiNum());
		}
		m.addAttribute("msg", msg);
		m.addAttribute("url",url);
		return "common/msg";
	}
	
	//내가 만든 밥상이 있을 때 보내는 메세지
	@PostMapping("/failedAddBabsang")
	public String failInsertBabsang(BabsangInfoVO babsang,  UserInfoVO userInfoVO,  Model m, HttpSession session) {
		String msg = "밥상 등록 실패";
		String url = "/babsang-insert";
		m.addAttribute("msg", msg);
		m.addAttribute("url",url);
		return "common/msg";
	}
	
	@GetMapping("/detail/{biNum}") // biNum으로 VO를 가져오는 방식
    public String detailBabsang(@PathVariable int biNum, Model m){
		// 밥상 상세페이지 컨트롤러 구현
        m.addAttribute("detail", babsangInfoService.getBabsangInfoVO(biNum));
        return "babsang/babsang-detail"; // 요청 jsp
    }
	
	@GetMapping("/deleteBabsang")
	public String deleteBabsang(Model m, @RequestParam("biNum") int biNum, UserInfoVO userInfoVO, HttpSession session) {
		UserInfoVO user = (UserInfoVO)session.getAttribute("user");
		String msg = "밥상 삭제 실패";
		String url = "/main";
		if(babsangInfoService.deleteBabsangInfo(biNum)) {
			user.setBiNum(0);
			msg = "밥상 삭제 성공";
			url = "/main";
		}
		m.addAttribute("msg",msg);
		m.addAttribute("url",url);
		return "common/msg";
	}
}