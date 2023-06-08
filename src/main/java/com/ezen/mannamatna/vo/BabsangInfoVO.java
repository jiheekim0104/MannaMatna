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
	private int uiBiNum; //밥상 생성 시 userInfo의 biNum 일단 중단
}