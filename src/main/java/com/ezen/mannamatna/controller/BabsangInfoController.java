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

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class BabsangInfoController {
	
	@Autowired
	private BabsangInfoService babsangInfoService;
	@Autowired 
	private UserInfoService userInfoService;
	 
	

	 @GetMapping("/main") public String goMain(BabsangInfoVO babsang, Model m){
		 List<BabsangInfoVO> babsangList = babsangInfoService.getBabsangInfoVOs(babsang); 
		 m.addAttribute("babsangList",babsangList);
		 return "babsang/babsang-list"; 
	 }
	 
/*	페이징 구현중
	@GetMapping("/main")
	public String goMain(@ModelAttribute BabsangInfoVO babsang, Model m){
		m.addAttribute("page", babsangInfoService.getPagingBansang(babsang));
		return "babsang/babsang-list";
	}
*/
	
	@GetMapping("/addBabsang")
	public String goCreateBabsang(){
		return "babsang/babsang-insert";
	}
	
	@PostMapping("/addBabsang")
	public String insertBabsang(BabsangInfoVO babsang,  UserInfoVO userInfoVO,  Model m, HttpSession session) {
		UserInfoVO userSession = (UserInfoVO)session.getAttribute("user");

		babsang.setUiNum(userSession.getUiNum()); // 세션의 uiNum >> 받아서 밥상의 uiNum으로 set
		log.info("userInfo_biNum ===> {}", babsang.getUiBiNum()); // 밥상이 생성되지않아 받아올게 없어서 0이 나옴
		String msg = "밥상 등록 실패";
		String url = "/babsang-insert";
		
		if(babsangInfoService.addBabsangInfo(babsang)) {// 밥상이 insert 되면
			int biNum = babsang.getUiBiNum(); // insert 할 때의 autoIncrement 된 밥상 번호를 받아옴
			log.info("babsang.uiBiNum ===> {}", biNum); // 확인
			
			userInfoVO.setUiNum(userSession.getUiNum());

			userInfoVO.setBiNum(biNum); // 유저의 biNum >> 밥상 insert 할 때 받아온 biNum으로 set
			log.info("userInfo.biNum ===> {}", userInfoVO.getBiNum()); // 확인
			userSession.setBiNum(biNum); // 세션의 biNum >> 밥상 insert 할 때 받아온 biNum으로 set 
			log.info("userSession.biNum ===> {}", userSession.getBiNum()); // 확인
			
			msg = "밥상 등록 성공";
			url = "/main";
			userInfoService.updateBiNum(userInfoVO); // insert성공 시 유저서비스의 update 실행
			log.info("==== 밥상 insert 끝 ====");
		}
		m.addAttribute("msg", msg);
		m.addAttribute("url",url);
		return "common/msg";
	}

	@GetMapping("/detail/{biNum}") // biNum으로 VO를 가져오는 방식
    public String detailBabsang(@PathVariable int biNum, Model m){
		// 밥상 상세페이지 컨트롤러 구현
        m.addAttribute("detail", babsangInfoService.getBabsangInfoVO(biNum));
        m.addAttribute("babsangUserList", userInfoService.getUserInfosByBiNum(biNum));
        return "babsang/babsang-detail"; // 요청 jsp
    }
	
	@GetMapping("/deleteBabsang")
	public String deleteBabsang(Model m, @RequestParam("biNum") int biNum, UserInfoVO userInfoVO, HttpSession session) {
		UserInfoVO userSession = (UserInfoVO)session.getAttribute("user");
		String msg = "밥상 삭제 실패";
		String url = "/main";
		if(babsangInfoService.deleteBabsangInfo(biNum)) {
			userInfoVO.setUiNum(userSession.getUiNum());

			userSession.setBiNum(0);//세션의 biNum >> 0으로 set
			log.info("userSession.biNum >>> {}",userSession.getBiNum());
			
			userInfoVO.setBiNum(0);//유저의 biNum >> 0으로 set
			log.info("user.biNum >>> {}",userInfoVO.getBiNum());
			
			msg = "밥상 삭제 성공";
			url = "/main";
			userInfoService.updateBiNum(userInfoVO); // delete 성공 시 유저서비스의 update 실행
		}
		m.addAttribute("msg",msg);
		m.addAttribute("url",url);
		return "common/msg";
	}
	
	// 비로그인 시 밥상 보려고 할 때
	@GetMapping("/cannotSeeBabsang")
	public String cannotSeeBabsang() {
		return "common/common";
	}
	
}