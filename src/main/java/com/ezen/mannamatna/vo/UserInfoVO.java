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
	
	private String nuiId;
	private String naverImgPath;
}
