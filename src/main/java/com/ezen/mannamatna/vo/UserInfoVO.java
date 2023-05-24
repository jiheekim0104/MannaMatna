package com.ezen.mannamatna.vo;

import lombok.Data;

@Data
public class UserInfoVO {
	private int uiNum;
	private String uiNickname;
	private String uiPwd;
	private String uiPhoto;
	private Boolean uiGender;
	private int uiAge;
	private int biNum;
}
