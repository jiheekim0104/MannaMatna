package com.ezen.mannamatna.vo;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserInfoVO {
	private MultipartFile uiFile;
	private int uiNum;
	private String uiNickname;
	private String uiPwd;
	private String uiFilepath;
	private Boolean uiGender;
	private int uiAge;
	private int biNum;
	private Date uiCredat;
	private String uiId;
	private int uiActive;
	private String uiDel;
	private int uiUserCnt; // 날짜별 유저수 조회용
	private long kuiId;
	private String kakaoImgPath;
	private String uiPhone;
	
	private String nuiId;
	private String naverImgPath;
	
	// 탈퇴요청, 정지유저 리스트 페이징
	private int page = 1; // 기본 디폴트 1페이지
	private int rows = 10; // 한페이지에 10개 객체
	
	private String smsConfirmNum;
}
