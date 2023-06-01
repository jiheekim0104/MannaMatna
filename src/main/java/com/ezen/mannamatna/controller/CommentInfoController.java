package com.ezen.mannamatna.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.mannamatna.service.CommentInfoService;
import com.ezen.mannamatna.vo.CommentInfoVO;

@Controller
@RequestMapping("/comment")
public class CommentInfoController {

	@Autowired
	CommentInfoService commentInfoService;
	
	// 연결 테스트용 작업했던거임!
	@GetMapping("/comment") // 추후 babsang-detail 쪽으로 매핑지어질 예정
	public String goComment(@ModelAttribute CommentInfoVO commentInfoVO, Model m) {
		List<CommentInfoVO> commentList = commentInfoService.getCommentInfos(commentInfoVO); // 서비스 호출
		m.addAttribute("commentList", commentList); // 모델에 댓글리스트 add
		// 이부분은 추후 basang id 와 mapping 예정
		return "babsang/babsang-detail";
	}
	
	@GetMapping("/list")
	@ResponseBody // 리스트를 화면에 바로 뿌린다.
	public List<CommentInfoVO> commentInfoList(@PathVariable int biNum){
		// 댓글 리스트
		return commentInfoService.getCommentInfos(biNum);
	}
}