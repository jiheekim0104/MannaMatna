package com.ezen.mannamatna.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageVO {
	String to;
	String content;
}