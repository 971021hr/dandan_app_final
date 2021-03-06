# dandan_app_final

### Dandan Smartmirror Project_Application

<img src="https://user-images.githubusercontent.com/31493835/139200109-0af92dd6-c1e5-451c-99cc-8636d6954762.png"  width="320" height="320"/><br>
한이음 ICT멘토링 프로젝트(공모전 입선)<br>
[프로젝트 영상 보러가기](https://youtu.be/Zfjd85sxkeU)<br>
[안드로이드 전체 영상 보러가기](https://youtu.be/PzXwCY-_qAM)


## 목차<br>
[1. 소개](#소개)<br>
[2. 프로젝트 개요](#프로젝트-개요)<br>
[3. 주요 기능](#주요-기능)<br>
[4. 개발 기간](#개발-기간)<br>
[5. 팀원](#팀원)<br>
[6. 프로젝트 흐름도](#프로젝트-흐름도)<br>
[7. 안드로이드 프로토타입](#안드로이드-프로토타입)<br>
[8. 프로젝트 동작 기능](#프로젝트-동작-기능)<br>
[9. 사용된 라이브러리](#사용된-라이브러리)<br>
[10. 사용된 node.js 목록 정리](#사용된-node-js-목록-정리)<br>
[11. 사용된 DB Table 목록 정리](#사용된-db-table-목록-정리)<br>

## 소개
- 스마트미러 프로젝트를 진행하며, 함께 필요한 어플리케이션을 제작했습니다. 
- (건강과 관련한)다양한 정보를 기록하고 확인할 수 있습니다.

## 프로젝트 개요
- 스마트미러와 연결할 수 있는 앱입니다.
- 스마트미러에서 운동한 정보를 어플리케이션에서 확인할 수 있습니다.
- 개별적인 어플리케이션으로 사용이 가능해, 운동 뿐 아니라 다양한 정보(물, 몸무게, 식단)를 입력할 수 있습니다. 
- 차트를 통해 사용자의 운동기록, 몸무게 기록을 일주일, 한달, 일 년 단위로 확인할 수 있습니다.

## 주요 기능
- 기록한 정보를 확인, 수정, 삭제
- 건강관리를 하며 필요한 정보 제공
- 물, 신체, 운동, 식단을 직접 기록
- 차트를 통해 변화 확인
- 스마트미러와 연결 

## 개발 기간
- 2021년 02월 ~ 10월

## 팀원
- 🐶 [방세미](https://github.com/semibang) 
- 🐰 [임혜림](https://github.com/HyerimLim)
- 🐱 [차현경](https://github.com/CHA-HK)

## 프로젝트 흐름도
![프로젝트 흐름도](https://user-images.githubusercontent.com/31493835/139018333-284b23c8-17b4-4f99-88de-598ae87060a6.png)
- 전체적인 프로젝트 흐름은 다음과 같습니다. 
- 현재 깃은 안드로이드만 다루고 있으며 스마트미러 관련 깃은 하단 링크를 참조해주시길 바랍니다.
> https://github.com/971021hr/tantan_webproject


## 안드로이드 프로토타입
![안드로이드 프로토타입](https://user-images.githubusercontent.com/31493835/139015717-61eb88ec-1141-4a11-8e11-539e7464c93d.jpg)

## 프로젝트 동작 기능
1. calender
> <img src="https://user-images.githubusercontent.com/31493835/139171771-26f67a2b-1ce8-45e4-a2bc-3646d9191700.gif"  width="180" height="320"/><br>
> - 어플의 메인 화면이자 달력 화면입니다.
> - Dot가 있는 날짜를 선택하면 상세 페이지로 이동합니다.
> - 상세 페이지에서 다양한 기록을 확인할 수 있으며, 수정, 삭제가 가능합니다.
> - 상세 페이지 상단에 있는 날짜를 클릭해 달력 페이지로 돌아갈 수 있습니다.

2. community
> <img src="https://user-images.githubusercontent.com/31493835/139171898-958a1520-0162-4bad-b4e7-7789718b53af.gif"  width="180" height="320"/><br>
> - 커뮤니티 탭 화면입니다.
> - 사용자는 운동과 식단 관련한 정보를 어플에서 얻을 수 있습니다.

3. add
> <img src="https://user-images.githubusercontent.com/31493835/139171929-48c9ff69-0b09-46fb-90cb-9f64066d3c67.gif"  width="180" height="320"/><br>
>  - 추가 탭 화면입니다.
>  - 운동, 물, 식단, 신체 정보를 입력할 수 있습니다.

4. chart
> <img src="https://user-images.githubusercontent.com/31493835/139171972-38102c08-70d3-4c61-b5b1-a294aaa78328.gif"  width="180" height="320"/><br>
> - 차트 탭 화면입니다.
> - 일주일, 한 달, 일 년 단위로 변화를 확인할 수 있습니다.

5. setting
> <img src="https://user-images.githubusercontent.com/31493835/139172020-2aaf12e3-73ff-4318-a959-f4c5d65d721c.gif"  width="180" height="320"/><br>
> - 설정 탭 화면입니다.
> - 회원가입, 로그인, 로그아웃을 할 수 있습니다.
> - 스마트미러와 연결할 수 있습니다.
> - 어플과 관련한 공지사항과 도움말을 확인할 수 있습니다.
> - 메일을 통해 개발자에게 피드백을 할 수 있습니다.

## 사용된 라이브러리
- material-calendarview : 커스텀 달력 생성을 위한 라이브러리
- swipemenulistview : 스와이프 동작으로 수정,삭제 구현을 위한 라이브러리
- mail / additionnal / activation : 앱에서 메일 발송을 하기 위한 라이브러리
- tedpermission : 차트 생성을 위한 라이브러리
- retrofit2 : 서버 연결을 위한 라이브러리

## 사용된 node js 목록 정리
- 캘린더
  - 달력 - 전체보기 - 가져오기
  - 달력 - 상세보기 - 운동가져오기
  - 달력 - 상세보기 - 물가져오기
  - 달력 - 상세보기 - 신체가져오기
  - 달력 - 상세보기 - 식단가져오기
  - 달력 - 상세보기 - 운동 삭제하기
  - 달력 - 상세보기 - 식단 삭제하기
  - 달력 - 상세보기 - 운동 수정 불러오기
  - 달력 - 상세보기 - 운동 수정 업데이트 하기
  - 달력 - 상세보기 - 식단 수정 불러오기
  - 달력 - 상세보기 - 식단 수정 업데이트 하기

- 커뮤니티
  - 커뮤니티/팁 - 식단 팁 가져오기
  - 커뮤니티/팁 - 운동 팁 가져오기
  - 커뮤니티/팁 - 운동 팁 - 상세보기
  - 커뮤니티/팁 - 식단 팁 - 상세보기

- 추가
  - 추가 - 운동
  - 추가 - 물
  - 추가 - 신체
  - 추가 - 식단

- 차트
  - 통계/차트 - 신체 - 일주일
  - 통계/차트 - 신체 - 한달
  - 통계/차트 - 신체 - 일년
  - 통계/차트 - 운동 - 일주일, 한달
  - 통계/차트 - 운동 - 일년

- 설정
  - 설정 - 로그인
  - 설정 - 회원가입
  - 설정 -  비밀번호 찾기(임시비밀번호)
  - 설정 - 개인정보 - 닉네임(이름) 변경
  - 설정 - 개인정보 - 탈퇴하기
  - 설정 - 개인정보 - 비밀번호 변경
  - 설정 - 공지사항
  - 설정 - 도움말
  - 설정 - 공지사항 - 상세보기
  - 설정 - 도움말 - 상세보기
  - 설정 - 스마트미러 연결

## 사용된 DB Table 목록 정리
![DBtable](https://user-images.githubusercontent.com/31493835/139174486-81a4dae5-000b-4b2a-9617-91a95fd25ac4.png)

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2F971021hr%2Fdandan_app_final&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)
