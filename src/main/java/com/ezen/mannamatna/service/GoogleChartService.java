package com.ezen.mannamatna.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.mannamatna.vo.UserInfoVO;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class GoogleChartService {

	@Autowired
	UserInfoService userInfoService; // 유저인포서비스 의존주입

	public JSONObject getChartData(UserInfoVO userInfoVO, HttpSession session) {
		List<UserInfoVO> items = userInfoService.getUserInfos(userInfoVO, session);
		log.info("구글차트서비스에서 의존주입받은 유저서비스 메소드실행 후 유저리스트==>{}", items); // 유저서비스 메소드 정상실행 확인
		JSONObject data = new JSONObject(); // 해당 서비스에서 jsonObject를 컨트롤러에 리턴한다.
		// json의 칼럼 객체
		JSONObject col1 = new JSONObject();
		JSONObject col2 = new JSONObject();
		JSONObject col3 = new JSONObject();

		// json 배열 객체, 배열에 저장할때는 JSONArray()를 사용
		JSONArray title = new JSONArray();
		col1.put("label", "uiNum"); // col1에 자료를 저장 ("필드이름","자료형")
		col1.put("type", "number");
		col2.put("label", "uiGender");
		col2.put("type", "boolean");
		col3.put("label", "uiCredat");
		col3.put("type", "number");

		title.add(col1);
		title.add(col2);
		title.add(col3);

		// json 객체에 타이틀행 추가
		data.put("cols", title);// 제이슨을 넘김

		JSONArray body = new JSONArray(); // json 배열을 사용하기 위해 객체를 생성
		for (UserInfoVO uiVO : items) { 

			JSONObject uiNum = new JSONObject(); // json오브젝트 객체를 생성
			uiNum.put("v", uiVO.getUiNum()); 

			JSONObject uiGender = new JSONObject(); // json오브젝트 객체를 생성
			uiGender.put("v", uiVO.getUiGender()); 

			JSONObject uiCredat = new JSONObject(); // json오브젝트 객체를 생성
			uiCredat.put("v", uiVO.getUiCredat()); 

			JSONArray row = new JSONArray(); // json 배열 객체 생성 (위에서 저장한 변수를 칼럼에 저장하기위해)
			row.add(uiNum); 
			row.add(uiGender); 
			row.add(uiCredat);

			JSONObject cell = new JSONObject();
			cell.put("c", row); // cell 2개를 합쳐서 "c"라는 이름으로 추가
			body.add(cell); // 레코드 1개 추가

		}
		data.put("rows", body); // data에 body를 저장하고 이름을 rows라고 한다.
		return data; // 이 데이터가 넘어가면 json형식으로 넘어가게되서 json이 만들어지게 된다.
	}
}