package com.ezen.mannamatna.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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

	public JSONObject getGenderChart(UserInfoVO userInfoVO, HttpSession session) {
		// 성별차트 서비스
		List<UserInfoVO> items = userInfoService.getUserInfos(userInfoVO, session);
		log.info("구글차트서비스에서 의존주입받은 유저서비스 메소드실행 후 유저리스트==>{}", items); // 유저서비스 메소드 정상실행 확인

		int countMale = 0; // 남사유저수를 담을 변수
		int countFemale = 0; // 여자유저수를 담을 변수
		int totalCount = items.size(); // 리스트의 사이즈 = 총 유저수
		for (UserInfoVO uiVO : items) {

			if (uiVO.getUiGender()) {
				// 참이면 남자
				countMale++;
			} else {
				// 거짓인경우 여자
				countFemale++;
			}
		}
		JSONObject jsonData = new JSONObject(); // 리턴할 JSONObject 생성
		JSONArray columns = new JSONArray(); // 컬럼 생성
		// 테이블형식으로 이해하면 좋습니다.
		// [col1, col2, ...] = cols
		// [row1, ..
		// [row2, ..
		// [row3, ...
		JSONObject cols1 = new JSONObject(); // 컬럼2생성
		cols1.put("label", "유저성별"); // 컬럼1의 이름
		cols1.put("type", "string"); // 컬럼1의 타입
		columns.add(cols1); // 전체 컬럼 JSONArray에 컬럼1 추가

		JSONObject cols2 = new JSONObject(); // 컬럼2 생성
		cols2.put("label", "성별회원수"); // 컬럼2 이름
		cols2.put("type", "number"); // 컬럼2 타입
		columns.add(cols2); // 전체 컬럼 JSONArray에 컬럼2 추가

		jsonData.put("cols", columns); // 리턴해줄 전체 JSONObject에 "cols"라는 이름으로 전체 컬럼을 추가

		JSONArray dataRows = new JSONArray(); // 컬럼에 맞춰 넣을 데이터객체 생성

		JSONObject dataRow1 = new JSONObject(); // 데이터는 [남자, 인원수] 형식으로 만들어주어야한다.
		JSONArray dataRow1Values = new JSONArray(); // 컬럼2가지의 데이터를 한번에 넣어줄 객체 생성
		JSONObject dataRow1Value1 = new JSONObject(); // 컬럼1의 데이터
		dataRow1Value1.put("v", "남자"); // 컬럼1의 데이터로 남자(string)를 넣음
		dataRow1Values.add(dataRow1Value1); // 전체 데이터에 컬럼1의 데이터를 넣음
		JSONObject dataRow1Value2 = new JSONObject(); // 컬럼2의 데이터
		dataRow1Value2.put("v", countMale); // 컬럼2의 데이터로 남자회원수(number)를 넣음
		dataRow1Values.add(dataRow1Value2); // 전체 데이터에 컬럼2의 데이터를 넣음
		// 이 두 데이터를 하나의 행으로 묶어서 [남자, countMale]형식으로 열 하나를 만들어준다.
		dataRow1.put("c", dataRow1Values); // 컬럼1데이터, 컬럼2데이터를 묶어서 하나의 열로 묶고
		dataRows.add(dataRow1); // 만들어진 열을 전체 데이터 한줄로 넣음.

		JSONObject dataRow2 = new JSONObject();
		JSONArray dataRow2Values = new JSONArray();
		JSONObject dataRow2Value1 = new JSONObject();
		dataRow2Value1.put("v", "여자");
		dataRow2Values.add(dataRow2Value1);
		JSONObject dataRow2Value2 = new JSONObject();
		dataRow2Value2.put("v", countFemale);
		dataRow2Values.add(dataRow2Value2);
		dataRow2.put("c", dataRow2Values);
		dataRows.add(dataRow2);

		// Add the data rows to the main JSONObject
		jsonData.put("rows", dataRows);
		return jsonData; // 이 데이터가 넘어가면 json형식으로 넘어가게되서 json이 만들어지게 된다.
	}

	public JSONObject getAgeChart(UserInfoVO userInfoVO, HttpSession session) {
		// 연령대 차트 서비스
		List<UserInfoVO> items = userInfoService.getUserInfos(userInfoVO, session);
		log.info("구글차트서비스에서 의존주입받은 유저서비스 메소드실행 후 유저리스트==>{}", items); // 유저서비스 메소드 정상실행 확인
		int teenager = 0; // 10대 인원수
		int twenties = 0; // 20대 인원수
		int thirties = 0; // 30대 인원수
		int forties = 0; // 40대 인원수
		int fifties = 0; // 50대 이상 인원수
		for (UserInfoVO user : items) {
			// 유저리스트에서 각 연령대별 인원수를 파악
			if (user.getUiAge() == 10) {
				teenager++;
				continue;
			}
			if (user.getUiAge() == 20) {
				twenties++;
				continue;
			}
			if (user.getUiAge() == 30) {
				thirties++;
				continue;
			}
			if (user.getUiAge() == 40) {
				forties++;
				continue;
			}
			if (user.getUiAge() == 50) {
				fifties++;
				continue;
			}
		}
		JSONObject jsonData = new JSONObject(); // 해당 서비스에서 jsonObject를 컨트롤러에 리턴한다.
		JSONArray columns = new JSONArray(); // 컬럼 생성

		JSONObject cols1 = new JSONObject(); // 첫번째 컬럼
		cols1.put("label", "유저연령대");
		cols1.put("type", "string");
		columns.add(cols1);

		JSONObject cols2 = new JSONObject();
		cols2.put("label", "연령대별회원");
		cols2.put("type", "number");
		columns.add(cols2);

		jsonData.put("cols", columns);

		JSONArray dataRows = new JSONArray();

		JSONObject dataRow1 = new JSONObject();
		JSONArray dataRow1Values = new JSONArray();
		JSONObject dataRow1Value1 = new JSONObject();

		dataRow1Value1.put("v", "10대");
		dataRow1Values.add(dataRow1Value1);
		JSONObject dataRow1Value2 = new JSONObject();
		dataRow1Value2.put("v", teenager);
		dataRow1Values.add(dataRow1Value2);
		dataRow1.put("c", dataRow1Values);
		dataRows.add(dataRow1);

		JSONObject dataRow2 = new JSONObject();
		JSONArray dataRow2Values = new JSONArray();
		JSONObject dataRow2Value1 = new JSONObject();

		dataRow2Value1.put("v", "20대");
		dataRow2Values.add(dataRow2Value1);
		JSONObject dataRow2Value2 = new JSONObject();
		dataRow2Value2.put("v", twenties);
		dataRow2Values.add(dataRow2Value2);
		dataRow2.put("c", dataRow2Values);
		dataRows.add(dataRow2);

		JSONObject dataRow3 = new JSONObject();
		JSONArray dataRow3Values = new JSONArray();
		JSONObject dataRow3Value1 = new JSONObject();

		dataRow3Value1.put("v", "30대");
		dataRow3Values.add(dataRow3Value1);
		JSONObject dataRow3Value2 = new JSONObject();
		dataRow3Value2.put("v", thirties);
		dataRow3Values.add(dataRow3Value2);
		dataRow3.put("c", dataRow3Values);
		dataRows.add(dataRow3);

		JSONObject dataRow4 = new JSONObject();
		JSONArray dataRow4Values = new JSONArray();
		JSONObject dataRow4Value1 = new JSONObject();

		dataRow4Value1.put("v", "40대");
		dataRow4Values.add(dataRow4Value1);
		JSONObject dataRow4Value2 = new JSONObject();
		dataRow4Value2.put("v", forties);
		dataRow4Values.add(dataRow4Value2);
		dataRow4.put("c", dataRow4Values);
		dataRows.add(dataRow4);

		JSONObject dataRow5 = new JSONObject();
		JSONArray dataRow5Values = new JSONArray();
		JSONObject dataRow5Value1 = new JSONObject();

		dataRow5Value1.put("v", "50대이상");
		dataRow5Values.add(dataRow5Value1);
		JSONObject dataRow5Value2 = new JSONObject();
		dataRow5Value2.put("v", fifties);
		dataRow5Values.add(dataRow5Value2);
		dataRow5.put("c", dataRow5Values);
		dataRows.add(dataRow5);
		// Add the data rows to the main JSONObject
		jsonData.put("rows", dataRows);

		return jsonData;
	}

	public JSONObject getCredatChart(HttpSession session) {
		// 날자별 가입인원수 동향 선차트
		// Get the current date
		// Get the current date
		List<UserInfoVO> items = userInfoService.getUserInfosByCredat(session);
		// 이 리스트는 [가입날짜, 인원수] 의 형식으로 조회된다.
		JSONObject jsonData = new JSONObject(); // 해당 서비스에서 jsonObject를 컨트롤러에 리턴한다.
		JSONArray columns = new JSONArray(); // 컬럼 생성

		// Generate the data for the last month

		JSONObject cols1 = new JSONObject(); // 첫번째 컬럼
		cols1.put("label", "가입날짜");
		cols1.put("type", "string");
		columns.add(cols1);

		JSONObject cols2 = new JSONObject(); // 두번째 컬럼
		cols2.put("label", "가입날짜별회원수");
		cols2.put("type", "number");
		columns.add(cols2);

		jsonData.put("cols", columns);

		JSONArray dataRows = new JSONArray();

		// Format the date as needed, e.g., "YYYY-MM-dd"

		LocalDate currentDate = LocalDate.now();
		LocalDate thirtyDaysAgo = currentDate.minusDays(30);

		List<LocalDate> allDates = new ArrayList<>();
		while (currentDate.isAfter(thirtyDaysAgo) || currentDate.isEqual(thirtyDaysAgo)) {
			allDates.add(currentDate);
			currentDate = currentDate.minusDays(1);
		}
		Collections.reverse(allDates);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		for (LocalDate date : allDates) {
			// 최근 30일 날짜 컬럼으로 데이터 반복하며 데이터에 추가
			String formattedDate = date.format(formatter);
			JSONObject dataRow = new JSONObject();
			JSONArray dataRowValues = new JSONArray();
			JSONObject dataRowValue1 = new JSONObject();
			JSONObject dataRowValue2 = new JSONObject();
			dataRowValue1.put("v", formattedDate);
			dataRowValues.add(dataRowValue1);
			for (UserInfoVO user : items) {
				dataRowValue2.put("v", 0);
				dataRowValues.add(dataRowValue2);
				if (formattedDate.equals(user.getUiCredat().toString())) {
					// 현재날짜별 유저 회원가입날짜가 일치하는 경우
					dataRowValue2.put("v", user.getUiUserCnt());
					dataRowValues.add(dataRowValue2);
					break;
				}
			}

			dataRow.put("c", dataRowValues);
			dataRows.add(dataRow);
		}
		jsonData.put("rows", dataRows);
		return jsonData;
	}
}