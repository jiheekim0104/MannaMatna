# **🍴Manna Matna😋**

<img width="80%" src="https://github.com/jiheekim0104/MannaMatna/assets/120540854/4d2bc797-6a48-40ed-b4af-16ec9e282403"/><br>
팀장 : 김지희<br>
팀원 : 오성철, 최재환<br>
개발 기간 : 2023.05.20 ~ 2023.06.30 (총 43일)<br>
주제 : 같이 식사할수있는 사람을 찾는 커뮤니티 사이트<br>
<br>

# **개요**

[🎬PPT 시연 영상 ]((https://www.youtube.com/watch?v=w5JAgxHHhw8))<br>
🎬홈페이지 시연 영상 (업로드 예정)<br>
<br>

# **📋개발목적**

## **기획 목적**


1. API 사용방법 학습 (카카오&네이버 SNS 연동 및 GOOGLE 차트)
2. 가장 기본적인 CRUD에서 시작하여 각자가 고도화 할 수 있는 정도까지 도전해볼것
   <br>

## **주제 선정**


점점 늘어나는 1인 가구의 비중, 오늘도 많은 사람들이 혼자서 밥을먹는다.<br>
혼자서 밥을 먹었을때 건강에도 좋지 않은 영향을 준다는 연구결과도있는데, <br>
맛집에 모여서 새로운 사람들을 만나고 함께 일상을 공유할수있는 건강한 공간을 만들어보고 싶었다. <br>
<br>

# **💻개발 환경/시스템 구성**

| 항목                | 내용                                                     |
| ------------------- | -------------------------------------------------------- |
| 언어                | Java11,HTML/CSS, JavaScript                              |
| 서버                | Springboot Embedded Web Server                           |
| 프레임워크          | SpirngBoot(2.7.12), Mybatis(2.3.1)                       |
| DB                  | MySQL Workbench(8.0.32)                                  |
| IDE                 | Eclipse IDE 2023-03 (4.27.0)                             |
| 협업 도구           | Git-hub, Zoom, KakaoTalk, GoogleSheet, GoogleSlide       |
| API 또는 라이브러리 | Google : Chart<br>Kakao : Login<br>Naver : Login<br>tawk |

<br>

# **↗Flowchart**



-최종 사진으로 넣을것
<br>

# **Entity Relationship Diagram**



-최종 사진으로 넣을것
<br>

# **💡Software Requirement Specification**



-최종 사진으로 넣을것
<br>

# **📆Work Breakdown Structure**



-최종 사진으로 넣을것
<br>

# **👀웹 구성요소**

## **🌟Intro(index) Page🌟**

-녹화가능

## **🌟Info Page🌟**

-녹화가능

## **🌟Main(babsang-list) Page🌟**

-메인

## **🌟Babsang-Insert Page🌟**

-메인이동

## **🌟Babsang-Detail Page🌟**

-메인이동

## **🌟Babsang-Comment Page🌟**

-메인이동

## **🌟Join Page🌟**

-메인이동

## **🌟Login Page🌟**

-메인이동

## **🌟Profile Page🌟**

-녹화가능

## **🌟Update Page🌟**

-프로필 -> 녹화가능

## **🌟Withdraw Page🌟**

-프로필 -> 녹화가능

## **🌟Eerror page🌟**

-녹화가능

## **🌟Admin Chart page🌟**

-녹화가능

## **🌟Admin UserList page🌟**

-녹화가능

<!-- 모든 페이지 추가 -->
<br>

# **⚒️Trouble Shooting⚒️**

### 2023.06.05

- [Uncaught ReferenceError: jQuery is not defined](https://joonpyo-hong.tistory.com/entry/JS-jQuery-is-not-defined-is-not-defined-%EC%98%A4%EB%A5%98)

- [@PathVariable](https://byul91oh.tistory.com/435)
  - 컨트롤러에 `@PathVariable int biNum`를 매개변수로 하니 request URI에 /comment/list?biNum=1 로 자동으로 매핑되며 컨트롤러에 해당 URI를 응답받아 처리하는 메소드가 없어 404 에러가 발생했다.

---

### 2023.06.08

- Ajax 댓글 수정 기능 : Ajax로 댓글 수정하는 기능 구현 중 댓글 작성 중 ciNum에 대한 정보가 모두 0으로 초기화되는 현상으로 인해, 수정하기 버튼을 눌렀을 때 해당하는 ciNum의 정보가 없어, 마이바티스가 UPDATE 쿼리문을 실행하지 않는 현상
  - `CommentInfoMapper.xml`의 댓글목록에 대한 정보를 가져오는 SELECT문에서 CI_NUM 컬럼이 빠져있어 VO에 해당 정보가 담기지 않았다.

---

### 2023.06.11

- 카카오 API 사용시, 토큰을 발급하는 과정에서 400 에러 발생하여 회원가입 또는 로그인 중 하나만 작동하는 현상

  - 초기에 카카오 개발자 Redirect URI을 두개로 나눠서 설정했는데, 코드상으로는 /kakaoPost/ 으로 회원가입과 로그인을 모두 연결했기때문이었다.

  - 토큰요청시 Redirect URI를 파라미터를 통해 전달해주는 방식으로 변경

  ```java
    KakaoToken kakaoToken = uiService.requestToken("/kakaoPost/",code); //카카오 토큰 요청하여 회원가입에서 사용하는 경우
    KakaoToken kakaoToken = uiService.requestToken("/kakaoLogin/",code); //카카오 토큰 요청시 로그인에서 사용하는 경우
  ```

  - 파라미터로 넘겨받은 내용을 Redirect URI로 지정

  ```java
    public KakaoToken requestToken(String addURI, String code) // addURI가 Redirect URI에서 변경되는 부분
    String redirectURI = "&redirect_uri=http://localhost" + addURI; // addURI를 합친 Redirect URI 만들었음
  ```

---

### 2023.06.11(2)

- 밥상 INSERT 후 생성된 PRIMARY KEY BI_NUM 을 리턴받아 해당 밥상 작성자 유저정보에 만들어진 BI_NUM 을 업데이트 해야하는 상황이었며, 해당 인서트 서비스 기능 정상적으로 실행되자마자 BI_NUM이 갱신되는 줄 알았으나, 해당 키값을 돌려받아야 하는 것을 알게되었다.

  - `BabsangInfoMapper.xml`의 <insert></insert> 중 <selectKey></selectKey> 추가 후 돌려받는 uiBiNum으로 유저인포에 업데이트 하도록 수정하여 해결하였다.

  ```xml
    <insert id="insertBabsangInfo">
      INSERT INTO BABSANG_INFO(
      BI_TITLE, BI_CONTENT, BI_MEETINGDAT, BI_MEETINGTIM,
      BI_HEADCNT, BI_FDCATEGORY, BI_CLOSED, UI_NUM)
      VALUES(
      #{biTitle}, #{biContent}, #{biMeetingDat}, #{biMeetingTim},
      #{biHeadCnt}, #{biFdCategory}, #{biClosed}, #{uiNum}
      <!-- 마지막에 auto increase 된 넘버 불러오는 mysql 메소드 -->
      <selectKey resultType="int" keyProperty="uiBiNum" order="AFTER">
        SELECT LAST_INSERT_ID();
      </selectKey>
      )
    </insert>
  ```

---

### 2023.06.13

- 카카오 API 연동 회원가입 및 로그인 구현 중 각 프로젝트 환경마다의 프로필업로드 경로 차이로 인한 프로필연동 안되는 현상

  - `System.getProperty("user.dir");` : 시스템프로퍼티로 각 프로젝트의 절대경로를 불러오도록 설정한 후 uploadFilePath에 프로젝트기준으로 저장하고자하는 폴더 경로를 합쳐준다.

  ```java
    private final String absolutePath = System.getProperty("user.dir"); // 시스템속성으로 프로젝트경로불러옴
    private final String uploadFilePath = absolutePath + "\\src\\main\\webapp\\resources\\upload";
  ```

---

### 2023.06.14

- 일반 가입유저 DB에 카카오 가입유저까지 한번에 넣었을때 정체성을 잃게되었다. 따라서 일반 가입유저의 DB는 유지하되 추후 추가기능 구현을 위한 일반가입유저와 카카오가입유저의 DB를 분리했고 이때 두 테이블에서 ui_num를 외래키로 사용하여 카카오 가입 유저 DB가 ui_num를 가지고 일반가입유저 DB를 참조하도록 함.
  - 일반유저가 이후에 SNS 연동하여 로그인이 더 간편해짐 (2023.06.22 구현완료)
  - 관리자가 SNS 연동 유저에 관한 관리가 용이해짐 (미구현)

---

### 2023.06.15 (1)

- 카카오 DB 분리 후 로그인 시 유저정보를 제대로 불러오지 않아 화면에 프로필 사진 등 연동이 되지 않는 현상.

- UserInfoMapper.xml : KUI_ID 로 찾아온 UI_NUM을 가지고 온 후 해당 UI_NUM 으로 UserInfoVO를 SELECT하는 쿼리문이 없어서 정보가 담기지 않았으며, 해당 쿼리문 추가

  ```xml
    <select id="selectUserInfoByKakao"
      resultType="com.ezen.mannamatna.vo.UserInfoVO">
      select * from user_info where ui_Num = #{uiNum}
    </select>
  ```

- UserInfoMapper.java : 해당 메소드 추가

  ```java
  UserInfoVO selectUserInfoByKakao(UserInfoVO userInfoVO);
  ```

- UserInfoService.java : kakaoLogin()에 selectKakaoUserInfo()를 실행한 후 얻어온 uiNum을 가진 UserInfoVO 객체를 다시 selectUserInfoByKakao(userInfoVO) 하여 해당하는 유저정보를 모두 가져오게 했다.

  ```java
    public boolean kakaoLogin(KakaoUserInfoVO kakaoUserInfoVO, HttpSession session) {
      log.info("확인하려는 유저 =>{}",kakaoUserInfoVO);
      kakaoUserInfoVO = uiMapper.selectKakaoUserInfo(kakaoUserInfoVO);
      log.info("돌려받은 유저 =>{}",kakaoUserInfoVO);
      if (kakaoUserInfoVO != null) {
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUiNum(kakaoUserInfoVO.getUiNum()); // 카카오 로그인 유저의 유저번호를 userInfoVO에 담기
        UserInfoVO newUserInfoVO = uiMapper.selectUserInfoByKakao(userInfoVO);
        // uiNum 정보만 가지고 있는 VO를 넣고 리턴은 다시 셀렉트문으로 찾은 모든 정보를 가지고 있는 uiVO 객체를 다시 돌려받는다.
        // uiVO와 kakaoVO가 연결되는것은 uiNum 인데 찾은 uiNum으로 uiVO를 셀렉트해서 찾는 쿼리문이 없었음
        // 매퍼에 해당 메소드 및 쿼리문 추가하여 kakaoLogin()에 추가함
        // 객체를 두개 생성하는게 비효율적이면 같은 userInfoVO에 계속 담아도 됨. 정상작동확인함
        log.info("카카오 로그인 서비스 =>{}",newUserInfoVO);
        session.setAttribute("user", newUserInfoVO);
        log.info("서비스에서 카카오 세션값확인={}", session.getAttribute("user"));
        return true;
      }
      return false;
    }
  ```

---

### 2023.06.15 (2)

- 네이버 API 사용시, 카카오 API와는 달리 state라는 요소가 추가되어있어서 6월 11일에 발생한 것과 똑같이 400 에러 발생
- 초기에는 아래와 같이 구현함
  ```java
      String clientId = "[클라이언트 아이디값]";// 애플리케이션 클라이언트 아이디값
  	  String clientSecret = "[클라이언트 시크릿값]";// 애플리케이션 클라이언트 시크릿값
      String  code = request.getParameter("code");
      String  state = request.getParameter("state");
  ```
- 이미 state를 발급 받았기때문에 아래와 같이 수정함
  ```java
    public NaverToken requestNaverToken(String addURI,String code, String state)  // 파라미터로 넘겨받고
    //	    code = request.getParameter("code");
    //	    state = request.getParameter("state"); //주석처리함
    String apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code" + "&client_id=" + clientId
  			+ "&client_secret=" + clientSecret + "&redirect_uri=" + redirectURI + "&code=" + code + "&state="
  			+ state; //요청주소
  ```

---

### 2023. 06. 19

- 네이버 로그인 기능 구현하고 로그인 성공까지 시간이 너무 오래 걸리는 현상 발생
- 초기에는 네이버에 토큰을 요청하고 해당 사용자를 찾아오는 과정에서 시간이 많이 걸리는 줄 알았으나, 그게 아니라 받아온 사용자의 이미지를 저장하는 과정에서 시간이 오래 소요되는것이었다. 다음은 수정전 코드이다.

  ```java
    URL imageUrl = null;
  		InputStream inputStream = null;
  		OutputStream outputStream = null;
  		String name = null;
  		try {
  			imageUrl = new URL(profile_image);
  			inputStream = imageUrl.openStream();
  			name = UUID.randomUUID().toString();
  			outputStream = new FileOutputStream(uploadFilePath + "\\" + name + ".jpg");

  			while (true) {
  				int data = inputStream.read();
  				if (data == -1) {
  					break;
  				}
  				outputStream.write(data); // 이미지 데이터값을 컴퓨터 또는 서버공간에 저장
  			}

  			inputStream.close();
  			outputStream.close();

  		} catch (Exception e) {
  			// 예외처리
  			e.printStackTrace();
  		} finally {
  			if (inputStream != null) {
  				inputStream.close();
  			}
  			if (outputStream != null) {
  				outputStream.close();
  			}
  		}

  		userInfoVO.setUiFilepath(uploadFilePath + "\\" + name + ".jpg");// 사진
  ```

- 이미지를 저장하지 않고 넘겨받은 유저의 프로필 사진을 바로 파일 경로로 저장되는 방식으로 바꿨다. 다음은 수정된 코드이다. 아주 잘 작동했다. 네이버는 잘못이 없었다.
  ```java
    String profile_image = (String) response.get("profile_image"); //http 주소
  	userInfoVO.setNaverImgPath(profile_image);
  ```

---

### 2023. 06. 20 (1)

- CommentInfoMapper.xml : 댓글작성한 시간(timestamp)로 저장된 mySQL의 CI_CREDAT, CI_CRETIM 데이터를 timestamp date_format(CI_CREDAT, '%Y. %m. %d'), date_format(CI_CRETIM, '%H시 %i분') 으로 가공하던 중 각 DATE_FORMAT()함수에 별칭으로 CI_CREDAT, CI_CRETIM 을 작성해주지 않아 계속 NULL값으로 출력되는 현상 해결하였다.

  ```xml
  <select id="selectCommentInfo" parameterType="int" resultType="com.ezen.mannamatna.vo.CommentInfoVO">
  SELECT CI_NUM, CI_CONTENT, date_format(CI_CREDAT, '%Y. %m. %d') CI_CREDAT,
  date_format(CI_CRETIM, '%H시 %i분') CI_CRETIM, UI_NUM, BI_NUM FROM COMMENT_INFO WHERE CI_NUM = #{ciNum}
  </select>
  <select id="selectCommentInfos" parameterType="int"
  	resultType="com.ezen.mannamatna.vo.CommentInfoVO">
  	SELECT u.UI_FILEPATH, u.UI_NICKNAME, c.CI_NUM, c.CI_CONTENT, date_format(c.CI_CREDAT, '%Y. %m. %d') CI_CREDAT,
  	date_format(c.CI_CRETIM, '%H시 %i분') CI_CRETIM, c.BI_NUM, c.UI_NUM FROM COMMENT_INFO c
  	JOIN USER_INFO u ON c.UI_NUM = u.UI_NUM WHERE c.BI_NUM = #{biNum}
  </select>
  ```

---

### 2023. 06. 20 (2)

- 자바스크립트의 동작에따른 CSS 변경을 원해서 찾아보다가 태그.style 형태로 js가 css를 제어할수있음을 알게 되어 만들고있는 버튼에 적용해봤다.
  - 다음은 수정 전 코드이다.
  ```java
    $('#idChk').val('Y');
  			alert("사용가능한 아이디입니다.");
  			document.getElementById("idChk").style.backgroundColor = "#c8c8c8";
  			document.getElementById("idChk").style.color = "#FFFFFF";
  			document.getElementById("idChk").style.border = "2px solid #c8c8c8";
  			document.getElementById("idChk").style.pointerEvents = "none";
  ```
  - 이렇게 작업했을때, 저 버튼의 기능을 되살려주기위해 아래와 같이 작업해보니 (원래 hover가 작동했음) 원하는 방향으로 버튼의 기능을 모두 (특히 hover) 되살릴수 없겠다고 생각했다.
  ```java
    $('#idChk').val('N'); // 중복확인 이후에 다시 아이디를 바꿨을 경우에 중복확인을 하지않은 상태로 바꿈
  	        document.getElementById("idChk").style.backgroundColor = "#FFFFFF";
  	        document.getElementById("idChk").style.color = "#FC522F";
  	        document.getElementById("idChk").style.border = "2px solid #FC522F";
  	        document.getElementById("idChk").style.pointerEvents = "auto";
  ```
  - 그래서 아래와같이 코드를 수정했다.
  ```css
  .selected {
    width: 100px;
    height: 30px;
    background-color: #c8c8c8;
    border: 2px solid #c8c8c8;
    border-radius: 0.5em;
    color: #ffffff;
    font-weight: bolder;
    padding: 0px 22px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 12px;
    margin-top: 10px;
    pointer-events: none;
  }
  ```
  ```java
    $('#idChk').addClass('selected');
    $('#idChk').removeClass('selected');
  ```

---

### 2023. 06. 21

- 가입날짜별 유저 수를 구한 후 관리자 페이지의 LineChart에 적용을 원하여 `UserInfoMapper.xml`에 아래에 해당하는 쿼리문을 추가하였고, `UserInfoVO.java`에 담을 변수 `int uiUserCnt`를 선언하였다.

  ```xml
  <select id="selectUserInfosByCredat" resultType="com.ezen.mannamatna.vo.UserInfoVO">
    <!-- 가입날짜(년-월-일)별로 유저수 조회 쿼리문 추가(구글차트용) -->
    SELECT DATE_FORMAT(UI_CREDAT, '%Y-%m-%d'), COUNT(*) FROM USER_INFO GROUP BY DATE_FORMAT(UI_CREDAT, '%Y-%m-%d')
    ORDER BY UI_CREDAT ASC;
    </select>
  ```

  ```java
  @Data
  public class UserInfoVO
    private int uiUserCnt; // 날짜별 유저수 조회용
  ```

- 이후 SELECT 한 `DATE_FORMAT(UI_CREDAT, '%Y-%m-%d'), COUNT(*)` 컬럼으로 MyBatis가 해당 UserInfoVO에서 맞는 필드를 찾지 못하여 에러가 발생하였다. 이후 sql함수로 가져온 데이터를 VO에 원하는 컬럼형식으로 별칭처리하여 주었더니 서비스에서 해당 데이터를 잘 매핑하여 처리되는 것을 확인하였다. 쿼리문 추가시 반복되는 실수이다. 잘 확인하도록 하자.
  ```xml
    <select id="selectUserInfosByCredat" resultType="com.ezen.mannamatna.vo.UserInfoVO">
    <!-- 가입날짜(년-월-일)별로 유저수 조회 쿼리문 추가(구글차트용) -->
    <!-- VO의 필드형식으로 별칭을 선언해주었다. -->
    SELECT DATE_FORMAT(UI_CREDAT, '%Y-%m-%d') UI_CREDAT, COUNT(*) UI_USERCNT FROM USER_INFO GROUP BY DATE_FORMAT(UI_CREDAT, '%Y-%m-%d')
    ORDER BY UI_CREDAT ASC;
    </select>
  ```

---

### 2023. 06. 24(1)

- 관리자 페이지 작성시 상위 메뉴탭을 클릭할때마다 해당페이지에서는 해당버튼이 애니메이션이 적용토록 원했지만, 회원목록이 없을 경우에는 애니메이션 적용이 제대로 작동하지 않았다.
- 해당 `userList-block.jsp`를 살펴보니 jstl <c:if>목록이 없을 때 조건</c:if>사이에 스크립트가 끼워져있어 회원목록이 없을 경우 스크립트가 제대로 작동하지 않았다.

- `userList-block.jsp` 수정 전

  ```jsp
  <c:if test="${!(empty pageUiActive2.list)}">
  <script>
    <!-- 해당 위치에 스크립트가 끼워져있어 정상작동하지 않았다. -->
    <script>
      let blockTitle1 = document.getElementById('blockTitle');
      let withdrawTitle1 = document.getElementById('withdrawTitle');
      withdrawTitle1.classList.add('cancle');
    </script>
  </script>
  </c:if>
  ```

- `userList-block.jsp` 수정 후 정상 애니메이션 적용 확인

  ```jsp
  <c:if test="${!(empty pageUiActive2.list)}">
    <script>
    <!-- 유저목록이 없는경우 실행하는 script -->
    </script>
  </c:if>
  <script>
      let blockTitle1 = document.getElementById('blockTitle');
      let withdrawTitle1 = document.getElementById('withdrawTitle');
      withdrawTitle1.classList.add('cancle');
  </script>
  ```

---

### 2023. 06. 24(2)

- 밥상 상세페이지에서 다른 유저의 프로필을 확인해보면 해당 로그인한 유저가 밥상을 만들지 않았음에도 사이드바의 버튼이 `밥상생성` -> `내 밥상보러가기`로 변경되는 현상을 파악하였으며, 이후 해당 현상과 관련있던 `sideBar.jsp` 와 해당 요청주소에 해당하는 `UserInfoController.java`를 확인해보았다.

- `UserInfoController.java` 수정 전

  ```java
    @GetMapping("/profile/{uiNum}")
    public String profileByUiNum(@PathVariable("uiNum") int uiNum, HttpSession session, Model m) {
      UserInfoVO userInfoVO = uiService.getUserInfoFromBabsang(uiNum);
      m.addAttribute("user", userInfoVO);
      // 다른 유저의 uiNum을 파라미터로 하는 프로필요청을 받을 경우 모델에 "user"라는 이름으로 attribute해주었다.
      return "user/user-profile";
    }
  ```

- `sideBar.jsp` 수정 전

  ```jsp
  <c:if test="${user.biNum == 0 && sessionScope.user.uiId != 'administer'}">
  <!-- 각 조건에서는 세션이 아닌 모델에 넣어준 객체 user의 정보로 조건을 체크하고있다는 것을 확인하였다. -->
    <a class="addBabsang" href="/addBabsang">밥상 생성</a>
  </c:if>
  <c:if test="${user.biNum != 0 && sessionScope.user.uiId != 'administer'}">
  <!-- 각 조건에서는 세션이 아닌 모델에 넣어준 객체 user의 정보로 조건을 체크하고있다는 것을 확인하였다. -->
    <a class="addBabsang" href="/detail/${user.biNum}">내 밥상<br>보러가기</a>
  </c:if>
  ```

- `sideBar.jsp` 수정 후 정상작동 확인

  ```jsp
  <c:if test="${sessionScope.user.biNum == 0 && sessionScope.user.uiId != 'administer'}">
  <!-- 로그인 후 세션에 담기는 객체를 이용하여 조건을 검사하도록 수정하였다. -->
    <a class="addBabsang" href="/addBabsang">밥상 생성</a>
  </c:if>
  <c:if test="${sessionScope.user.biNum != 0 && sessionScope.user.uiId != 'administer'}">
  <!-- 로그인 후 세션에 담기는 객체를 이용하여 조건을 검사하도록 수정하였다. -->
    <a class="addBabsang" href="/detail/${user.biNum}">내 밥상<br>보러가기</a>
  </c:if>
  ```

---

### 2023. 06. 24(3)

- 관리자의 차트페이지(겟매핑) 직접 접근 시 어느 누구라도 로그인이 되어있다면 바로 접근이 가능하다는 것을 확인하였고 이에 해당하는 `common.jsp`를 확인하였으며, 해당 페이지는 유저의 로그인이 되어있지 않다는 조건으로만 세션객체를 검사하고 있었다. 이후 관리자인 유저만 관리자페이지에서 세션검사가 실행되도록 `admin.jsp`를 추가하여 관리자가 접근가능한 모든 jsp에 `include` 처리하였다.
- `admin.jsp`

  ```jsp
  <%@page import="com.ezen.mannamatna.vo.UserInfoVO"%>
  <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%
  UserInfoVO adminChk = (UserInfoVO) session.getAttribute("user");
  if(!adminChk.getUiId().equals("administer")){
  %>
  <script>
  alert('관리자 권한이 필요한 서비스입니다. 🍔');
  location.href = '/main';
  </script>
  <%
  return;
  }
  %>
  ```

- 관리자 jsp에 `<%@ include file="/WEB-INF/views/common/common.jsp"%>` 와 함께 `<%@ include file="/WEB-INF/views/common/admin.jsp"%>`를 모두 추가해주었다.
