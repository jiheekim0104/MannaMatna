package com.ezen.mannamatna.vo;

import lombok.Data;

@Data
public class BabsangInfoVO {
	private int biNum;
	private String biTitle;
	private String biContent;
	private String biMeetingDat;
	private String biMeetingTim;
	private int biHeadCnt;
	private String biCreateDat;
	private String biFdCategory;
	private boolean biClosed;
	private int uiNum;
	private int uiBiNum;//밥상이 생성되며 autoincrement되는 biNum을 따로받음
	private int uiNickname;
	private int biUserCnt;
	//페이징 
	private int page = 1;
	private int rows = 4;
}