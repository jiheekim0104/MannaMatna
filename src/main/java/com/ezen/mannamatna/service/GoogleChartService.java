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
//		JSONObject col1 = new JSONObject();
//		JSONObject col2 = new JSONObject();
//		JSONObject col3 = new JSONObject();
//		
//		//{"변수명" : [{},{},{}], "변수명" : "값"}
//		col1.put("label", "userCount"); // col에 자료를 저장
//		col1.put("type", "string"); // col에 자료를 저장
//		col2.put("label", "male");
//		col2.put("male", "number");
//		col3.put("label", "female");
//		col3.put("type", "number");
//
//		// json 배열 객체, 배열에 저장할때는 JSONArray()를 사용
//		JSONArray title = new JSONArray();
//
//		title.add(col1);
//		title.add(col2);
//		title.add(col3);
//		
//		// json 객체에 타이틀행 추가
//		data.put("cols", title);// 제이슨을 넘김
//
//		JSONArray body = new JSONArray(); // json 배열을 사용하기 위해 객체를 생성
//
//		int countMale = 0; // 남사유저수를 담을 변수
//		int countFemale = 0; // 여자유저수를 담을 변수
//		int totalCount = items.size(); // 리스트의 사이즈 = 총 유저수
//		for (UserInfoVO uiVO : items) {
//
//			if (uiVO.getUiGender()) {
//				// 참이면 남자
//				countMale++;
//			} else {
//				// 거짓인경우 여자
//				countFemale++;
//			}
//		}
//
//		JSONObject totalUser = new JSONObject(); // json오브젝트 객체를 생성
//		totalUser.put("v", "totalCount");
//
//		JSONObject male = new JSONObject(); // json오브젝트 객체를 생성
//		male.put("v", countMale);
//
//		JSONObject female = new JSONObject(); // json오브젝트 객체를 생성
//		female.put("v", countFemale);
//
//		JSONArray row = new JSONArray(); // json 배열 객체 생성 (위에서 저장한 변수를 칼럼에 저장하기위해)
//		row.add(totalUser);
//		row.add(male);
//		row.add(female);
//		
//		body.add(row);
//		JSONObject cell = new JSONObject();
//		cell.put("c", row); // cell 2개를 합쳐서 "c"라는 이름으로 추가
//		body.add(cell); // 레코드 1개 추가
//		
//		data.put("rows", body); // data에 body를 저장하고 이름을 rows라고 한다.
		
		
		// 샘플데이터 활용한 다른 방법..내일본다.....
        JSONObject jsonData = new JSONObject();

        // Create a JSONArray to hold the columns
        JSONArray columns = new JSONArray();

        // Create the "Sex" column
        JSONObject sexColumn = new JSONObject();
        sexColumn.put("label", "Sex");
        sexColumn.put("type", "string");
        columns.add(sexColumn);

        // Create the "Membership" column
        JSONObject membershipColumn = new JSONObject();
        membershipColumn.put("label", "Membership");
        membershipColumn.put("type", "number");
        columns.add(membershipColumn);

        // Add the columns to the main JSONObject
        jsonData.put("cols", columns);

        // Create a JSONArray to hold the data rows
        JSONArray dataRows = new JSONArray();

        // Add the data rows as JSONObjects
        JSONObject dataRow1 = new JSONObject();
        JSONArray dataRow1Values = new JSONArray();
        JSONObject dataRow1Value1 = new JSONObject();
        dataRow1Value1.put("v", "Male");
        dataRow1Values.add(dataRow1Value1);
        JSONObject dataRow1Value2 = new JSONObject();
        dataRow1Value2.put("v", 70);
        dataRow1Values.add(dataRow1Value2);
        dataRow1.put("c", dataRow1Values);
        dataRows.add(dataRow1);

        JSONObject dataRow2 = new JSONObject();
        JSONArray dataRow2Values = new JSONArray();
        JSONObject dataRow2Value1 = new JSONObject();
        dataRow2Value1.put("v", "Female");
        dataRow2Values.add(dataRow2Value1);
        JSONObject dataRow2Value2 = new JSONObject();
        dataRow2Value2.put("v", 30);
        dataRow2Values.add(dataRow2Value2);
        dataRow2.put("c", dataRow2Values);
        dataRows.add(dataRow2);

        // Add the data rows to the main JSONObject
        jsonData.put("rows", dataRows);
		return jsonData; // 이 데이터가 넘어가면 json형식으로 넘어가게되서 json이 만들어지게 된다.
	}
}