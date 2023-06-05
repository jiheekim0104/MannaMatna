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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.annotation.SessionScope;

import com.ezen.mannamatna.service.CommentInfoService;
import com.ezen.mannamatna.service.UserInfoService;
import com.ezen.mannamatna.vo.CommentInfoVO;
import com.ezen.mannamatna.vo.UserInfoVO;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/comment")
@Log4j2
public class CommentInfoController {

	@Autowired
	CommentInfoService commentInfoService;
	@Autowired
	UserInfoService userInfoService;
	// 연결 테스트용 작업했던거임!
//	@GetMapping("/comment") // 추후 babsang-detail 쪽으로 매핑지어질 예정
//	public String goComment(@ModelAttribute CommentInfoVO commentInfoVO, Model m) {
//		List<CommentInfoVO> commentList = commentInfoService.getCommentInfos(commentInfoVO); // 서비스 호출
//		m.addAttribute("commentList", commentList); // 모델에 댓글리스트 add
//		// 이부분은 추후 basang id 와 mapping 예정
//		return "babsang/babsang-detail";
//	}
	
	@GetMapping("/list")
	@ResponseBody // 리스트를 화면에 바로 뿌린다.
	public List<CommentInfoVO> commentInfoList(int biNum){
		// 파라미터로 @PathVariable 빼니깐 동작함...uri와 관련이 있는 것 같다..!
		// 댓글 리스트
		return commentInfoService.getCommentInfos(biNum);
	}
	
	@PostMapping("/insert")
	@ResponseBody // 댓글 화면에 바로 출력
	public int commentInfoInsert(@RequestParam int biNum, @RequestParam String ciContent, HttpSession session) {
		CommentInfoVO commentInfoVO = new CommentInfoVO();
		UserInfoVO userInfoVO = (UserInfoVO) session.getAttribute("user");
		int uiNum = userInfoVO.getUiNum();
		log.info("세션에담겼어?!!!!======>{}", uiNum);
		commentInfoVO.setBiNum(biNum);
		commentInfoVO.setUiNum(uiNum);
		commentInfoVO.setCiContent(ciContent);
		return commentInfoService.insertCommentInfo(commentInfoVO);
	}
}