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
	//밥상이 완전히 생성되기 전까지는 biNum을 사용할 수 없어서 따로 담으려고 만들었슴니다 
	private int uiBiNum;
}