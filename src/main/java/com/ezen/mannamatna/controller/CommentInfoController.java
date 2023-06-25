package com.ezen.mannamatna.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.mannamatna.service.CommentInfoService;

import com.ezen.mannamatna.vo.CommentInfoVO;
import com.ezen.mannamatna.vo.UserInfoVO;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/comment")
@Log4j2
public class CommentInfoController {

	@Autowired
	CommentInfoService commentInfoService;

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
	public List<CommentInfoVO> commentInfoList(@RequestParam int biNum){
		// 파라미터로 @PathVariable 빼니깐 동작함...uri와 관련이 있는 것 같다..!
		// 댓글 리스트
		log.info("왜 ci 타임이 null인거야 대체???{}", commentInfoService.getCommentInfosService(biNum));
		return commentInfoService.getCommentInfosService(biNum);
	}
	
	@PostMapping("/insert")
	@ResponseBody // 댓글 화면에 바로 출력
	public int commentInfoInsert(@RequestParam int biNum, @RequestParam String ciContent, HttpSession session) {
		CommentInfoVO commentInfoVO = new CommentInfoVO(); // 새로 저장할 VO객체 생성
		UserInfoVO userInfoVO = (UserInfoVO) session.getAttribute("user"); // 세션에 저장된 유저정보 가져옴
		commentInfoVO.setBiNum(biNum); // 해당 게시물 번호를 댓글정보에 넣어준다.
		commentInfoVO.setCiContent(ciContent); // 내용 저장
		commentInfoVO.setUiNum(userInfoVO.getUiNum()); // 세션에서 가져온 uiNum 담아준다.
		commentInfoVO.setUiFilepath(userInfoVO.getUiFilepath()); // 세션정보의 프로필사진정보 넣어준다.
		commentInfoVO.setUiNickname(userInfoVO.getUiNickname()); // 세션정보의 닉네임 넣어준다.
		log.info("댓글작성하고 ciNum이몇이지?{}", commentInfoVO.getCiNum());
		return commentInfoService.insertCommentInfoService(commentInfoVO);
	}
	@PostMapping("/update") // 댓글 수정(내용만)
	@ResponseBody
	public int commentInfoUpdate(@RequestParam int ciNum, @RequestParam String ciContent) {
		CommentInfoVO commentInfoVO = new CommentInfoVO(); // 새로 저장할 VO객체 생성
		commentInfoVO.setCiNum(ciNum); // 해당 댓글 번호를 댓글정보에 넣어준다.
		commentInfoVO.setCiContent(ciContent); // 내용 저장
		log.info("서비스가 잘 실행이되고있나?========>{}", commentInfoService.updateCommentInfoService(commentInfoVO));
		log.info("ciNum이몇이야?========>{}", ciNum);
		return commentInfoService.updateCommentInfoService(commentInfoVO);
	}
	@PostMapping("/delete/{ciNum}")
	@ResponseBody
	public int commentInfoDelete(@PathVariable int ciNum) {
		log.info("삭제컨트롤러왔징?ciNum======{}", ciNum);
		return commentInfoService.deleteCommentInfoService(ciNum);
	}
}