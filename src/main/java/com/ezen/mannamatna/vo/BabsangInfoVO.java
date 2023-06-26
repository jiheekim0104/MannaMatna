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
	private int uiBiNum;//밥상이 생성시 autoIncrement될 biNum을 받음
	private int uiNickname;
	private int biUserCnt;
	private int biCnt; // 맛남이루어진 밥상 개수 담을 필드
	//페이징 
	private int page = 1;
	private int rows = 4;
	//위치
	private double lat;
	private double lng;
}