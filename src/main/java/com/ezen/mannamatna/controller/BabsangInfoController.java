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
import com.ezen.mannamatna.vo.BabsangInfoVO;
import com.ezen.mannamatna.vo.UserInfoVO;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class BabsangInfoController {
	
	@Autowired
	private BabsangInfoService babsangInfoService;
	
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
	public String insertBabsang(BabsangInfoVO babsang, Model m, HttpSession session) {
		UserInfoVO user = (UserInfoVO)session.getAttribute("user");
		babsang.setUiNum(user.getUiNum());
		String msg = "밥상 등록 실패";
		String url = "/babsang-insert";
		if(babsangInfoService.addBabsangInfo(babsang)) {
			msg = "밥상 등록 성공";
			url = "/main";
		}
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
	
	@GetMapping("deleteBabsang")
	public String deleteBabsang(Model m,  @PathVariable int biNum) {
		String msg = "밥상 삭제 실패";
		String url = "/main";
		if(babsangInfoService.deleteBabsangInfo(biNum)) {
			msg = "밥상 삭제 성공";
			url = "/main";
		}
		m.addAttribute("msg",msg);
		m.addAttribute("url",url);
		return "common/msg";
	}
}