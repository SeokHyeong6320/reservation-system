# 개요
### 매장 테이블 예약 서비스

사용 기술 : Spring, Spring Security, Jpa, MariaDB, QueryDsl

목표 : 식당이나 점포를 이용하기 전에 미리 예약을 하여 편하게 식당/점포를 이용할 수 있는 서비스 구현

---

## 패키지 구성

### auth
- 회원가입, 로그인

### common

### customer
- 예약, 후기

### kiosk
- 방문 확인 기능

### partner
- 예약 승인, 상점 등록 수정 삭제

### security
- Spring Security & JWT를 사용한 인증, 인가

### store
- 상점검색


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


- [ ] 키오스크 통한 방문 확인
  - 예약 10분 전 까지만 가능


- [ ] 예약사용 후에 리뷰 작성
  - 예약자 인지 확인 및 작성, 수정, 삭제 
  - 수정은 리뷰 작성자만, 삭제 권한은 리뷰를 작성한 사람과 매장의 관리자(점장 등)만


## 점장
- [x] 상점 등록
  - 매장 등록 위해서는 파트너 가입 필요 (가입 후 바로 이용 가능)
  - 매장 정보 : 매장 명 / 상점위치 / 상점 설명
- [x] 상점 수정
- [ ] 상점 삭제


- [ ] 들어온 예약 승인 / 거절
  - 예약 정보 확인 (날짜별 시간 테이블 목록)
  - 사용자가 예약 후에 점장이 예약을 승인해야 사용 가능.