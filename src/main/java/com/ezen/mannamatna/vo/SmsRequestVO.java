package com.ezen.mannamatna.vo;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmsRequestVO {
	String type;
	String contentType;
	String countryCode;
	String from;
	String content;
	List<MessageVO> messages;
}
