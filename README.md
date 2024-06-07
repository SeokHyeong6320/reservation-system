# 개요
### 매장 테이블 예약 서비스

사용 기술 : Spring, Spring Security, Spring Cloud Eureka, Spring Cloud Gateway, Jpa, MariaDB, QueryDsl, Kafka

목표 : 식당이나 점포를 이용하기 전에 미리 예약을 하여 편하게 식당/점포를 이용할 수 있는 서비스 구현

---

## 모듈 구성

### discovery-service

### apigateway-service

### common

### domain

### kiosk-service

### api
- ### security-service

- ### customer-service

- ### partner-service

- ### store-service

- ### reservation-service

- ### review-service




---


## 공통 적용사항
- Spring Security 사용한 접근제한
    - Jwt token 사용
    - 파트너, 일반고객 각각 접근 가능한 엔드포인트 분리

## 회원 가입
- [x] 예약 / 등록을 진행하기 위해서는 회원 가입 필수
- [x] 회원가입하면 CUSTOMER로 가입 -> 파트너 회원 가입하면 PARTNER로 승급

## 고객
- [x] 매장 정보 검색
  - 상점 목록 : 가나다순, 별점순, 거리순


- [x] 예약 
  - 요청 시 필요 항목 : 상점, 날짜, 시간, 내 정보(전화번호)
  - 예약 가능 여부 확인 후 예약 진행
  - 예약 후 방문 시 인증을 위해 8자리 랜덤 문자열 반환


- [x] 키오스크 통한 방문 확인
  - 요청 시 필요 항목 : 예약아이디, 이메일, 인증코드
  - 예약 10분 전 까지만 가능


- [x] 예약사용 후에 리뷰 작성
  - 예약자 인지 확인 및 작성
- [x] 리뷰 수정
  - 리뷰 작성자만
- [x] 리뷰 삭제
  - 리뷰를 작성한 사람과 매장의 관리자(점장 등)만


## 점장
- [x] 상점 등록
  - 매장 등록 위해서는 파트너 가입 필요 (가입 후 바로 이용 가능)
  - 매장 정보 : 매장 명 / 상점위치 / 상점 설명
- [x] 상점 수정
- [x] 상점 삭제
  - 상점 삭제되면 관련 예약, 리뷰 모두 삭제

- [x] 예약 정보 확인 (날짜별 시간 테이블 목록)

- [x] 들어온 예약 승인 / 거절
  - 사용자가 예약 후에 점장이 예약을 승인해야 사용 가능.

## 키오스크
- [x] 해당 상점의 키오스크에서만 방문 체크 가능
- [x] 상점 등록할 때 키오스크도 등록되도록 구성