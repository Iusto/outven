# 🚀 [Outven - 게임 커뮤니티 게시판 프로젝트](https://github.com/Iusto/outven) (개인 리팩토링 완료)

## 📌 프로젝트 개요

Outven은 게임 커뮤니티 ‘Inven’을 모티브로 제작한 **CRUD 기반 게시판 시스템**입니다.  
2024년 2월, **비전공자 중심의 4인 팀이 약 3주간 1차 개발**, 이후 **혼자서 약 1개월간 전면 리팩토링**을 진행했습니다.

- Mustache → Thymeleaf 전환  
- Spring Security, Redis 세션 관리, 트랜잭션 설계  
- RESTful 댓글 API, 페이징 성능 개선

---

## 📚 목차

- [📌 프로젝트 개요](#-프로젝트-개요)  
- [👥 팀 개발 및 역할 분담](#-팀-개발-및-역할-분담)  
- [🔍 주요 기능 (Before vs After)](#-주요-기능-before-vs-after)  
- [📉 리팩토링 전 문제점](#-리팩토링-전-문제점)  
- [🛠 기술 스택](#-기술-스택)  
- [📈 성능 및 구조 개선 지표](#-성능-및-구조-개선-지표)  
- [🧩 시스템 아키텍처](#-시스템-아키텍처)  
- [🗃 ERD (DB 구조도)](#-erd-db-구조도)  
- [🚀 실행 방법](#-실행-방법)  
- [📅 향후 개선 방향](#-향후-개선-방향)  
- [🙌 후기 및 성장](#-후기-및-성장)

---

## 👥 팀 개발 및 역할 분담

- 총 4인 팀 구성 (비전공자 위주)  
- **팀장 역할 수행**: 기획 조율, 일정 관리, Git 협업 관리 등 총괄 리딩  
- 역할 분담:
  - 김정규: 회원가입/로그인, 이메일 인증, 추천 게시판 자동화, 전체 게시판 및 댓글 기능, DB 설계, 리팩토링 총괄  
  - 팀원1: 관리자 기능, 블랙리스트 관리 UI/로직  
  - 팀원2: 게시판 프론트엔드 마크업, Bootstrap 레이아웃, Ajax 테스트  
  - 팀원3: 게시글 검색 기능, 공통 레이아웃 구성, 페이징 처리 지원

---

## 🔍 주요 기능 (Before vs After)

| 항목 | Before (팀 개발) | After (개인 리팩토링) |
|------|------------------|------------------------|
| 회원가입 / 로그인 / 로그아웃 | 구현 (Session 기반) | **Spring Security + BCrypt 적용**, 로그인 실패 핸들링 추가 |
| 이메일 인증 | 기본 컨트롤러 존재 | **Ajax 기반 인증번호 발송/검증 기능 정식 도입** |
| 게시판 CRUD | 게시판별 Controller 다중 구현 (`freeBoardController` 등) | **단일 `BoardController`로 통합**, 게시판 타입은 Enum 기반 분기 처리 |
| 추천 게시판 자동화 | 기본 구현 | **추천 수 ≥ 기준치 → 자동 게시판 분류 기능 고도화** |
| 댓글 기능 | DAO 중심 댓글 처리 | **MVC 컨트롤러(CommentController) 및 REST API(CommentRestController) 도입**, 비동기 통신을 통한 UX 향상 |
| 관리자 기능 | 등급 수정 일부만 구현 | **회원 검색, 경험치 일괄 수정, 블랙리스트 관리 등 확장** |
| ID/이메일 중복 확인 | Naver SMTP 기반 이메일 전송, 설정 외부 처리 | **Gmail SMTP 설정을 application.properties로 통합, 인증번호 전송 구조 정비 및 Ajax 기반 중복 검사 기능 구현** |
| 비밀번호 처리 | 평문 저장 | **BCrypt 암호화 적용 → 보안 표준 준수** |
| 세션 처리 | 내장형 기본 설정 | **Redis 기반 세션 관리 → 중복 로그인 차단** |
| 템플릿 구조 | Mustache + 페이지별 분산 템플릿 | **Thymeleaf 공통 템플릿 구성 (fragment화)** |
| 파일 업로드 처리 | Controller 직접 처리 | **FileService 도입으로 SRP(단일 책임 원칙) 적용 및 테스트 용이성 확보** |
| 예외 처리 | 미흡 | **try-catch 기반 지역 예외 처리 수행, 전역 예외 핸들러(@ControllerAdvice) 적용** |
| 코드 계층 구조 | Controller 중심 단일 로직 | **Service / DTO / Entity / Validator 등 계층 분리, 재사용성 강화** |

---

## 📉 리팩토링 전 문제점

- 게시판별 컨트롤러 중복 구현  
- Mustache 템플릿 분산 → UI 재사용성 낮음  
- 댓글, 보안, 이메일 인증 미흡  
- 파일 업로드·댓글 처리 로직 Controller 내부 혼재  
- 비즈니스 로직과 Controller 분리 미흡  
- 외장 Tomcat 배포 불가  
- 예외 처리·Validation 부재  
- 디렉터리 구조 및 의존성 설정 불균일  
- 비밀번호 평문 저장 → 보안 취약

---

## 🛠 기술 스택

| 분류 | 기술 |
|------|------|
| Backend | Java 17, Spring Boot, Spring Security, JPA, Lombok |
| Frontend | Thymeleaf, Bootstrap 5.3, JavaScript, jQuery |
| Infra | Oracle 18c XE, Redis, STS4, Gradle, 외장 Tomcat (WAR) |
| 기타 | Ajax, BCrypt, ResponseEntity, ERD 설계 |

---

## 📈 성능 및 구조 개선 지표

| 항목 | Before | After | 개선 효과 |
|------|--------|-------|-----------|
| 템플릿 렌더링 속도 | 평균 120ms | 70ms | Thymeleaf 구조 통합으로 응답 속도 약 40% 개선 |
| 컨트롤러 수 | 15개 이상 | 11개로 통합 | 중복 제거로 유지보수 범위 약 27% 감소 |
| 댓글 API 구조 | 없음 또는 Controller 내부 | `CommentRestController` 별도 분리 | UX 향상 및 SRP 원칙 적용 |
| 게시판 구조 처리 | 게시판별 개별 구현 | `BoardController` + Enum 활용 | 통합 처리로 일관성과 확장성 확보 |
| 파일 처리 구조 | Controller 직접 처리 | FileService 분리 | 테스트 용이성 및 유지보수성 향상 |
| 코드 계층화 | 일부 Service/DTO 존재 | DTO/Service/Entity/Validator 레이어 분리 | 테스트 작성 및 유지보수 용이성 향상 |
| API 응답 형식 | 다양한 반환 방식 | `ResponseEntity`로 통일 | API 일관성 및 문서화 편의성 향상 |
| 보안 기능 수 | 없음 | 최소 3개 이상 도입 | 인증/인가, 암호화, 세션 처리 등 보안 표준 준수 |

> 📌 위 지표는 로컬 환경 (32GB RAM, Ryzen 7 7800X, SSD)에서 Chrome DevTools 기준 측정

---

## 🧩 시스템 아키텍처

![Outven 아키텍처](/docs/architecture.png)

Spring Boot 기반으로 Controller, Service, Repository, Security, Redis, Oracle DB 등 계층 명확히 분리  
→ 유지보수성과 확장성 강화

---

## 🗃 ERD (DB 구조도)

![Outven ERD](/docs/erd.png)

- 유저, 게시판, 게시글, 댓글, 권한, 이메일 인증 테이블 구성  
- `User` ↔ `Role`, `Post`, `Comment`, `EmailVerification` 관계  
- `Post` ↔ `Board` 연결 구조

---

## 🚀 실행 방법

```bash
git clone https://github.com/yourname/outven.git
cd outven
./gradlew clean build

```

> 📌 Oracle DB 설정 필요 (application.yml 또는 .properties)
> 📌 외장 Tomcat WAR 배포 방식 지원

---

## 📅 향후 개선 방향

* Redis 기반 이메일 인증 상태 저장
* JUnit / Mockito 기반 테스트 코드 작성 확대
* 게시판 정렬/필터링 조건 강화
* 관리자 통계 기능 (활동 로그, 회원 분석 등)

---

## 🙌 후기 및 성장

처음 4인 팀으로 시작한 Outven은 비전공자 중심 팀의 협업과 설계 미숙으로 기능이 분산되고 구조가 복잡했습니다.
이에 따라 프로젝트 종료 후 **약 1개월간 혼자서 전면 구조 리팩토링**을 수행했습니다.

이 과정에서 Spring 기반 아키텍처 설계, 보안 적용, 성능 개선을 실무적으로 경험했으며,
**혼자서도 실무형 웹 프로젝트를 끝까지 완성할 수 있다는 자신감**과 함께 **기술적으로 한 단계 성장할 수 있었습니다.**
