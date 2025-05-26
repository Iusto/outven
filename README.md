
# 🚀 Outven - 게시판 아키텍처 리팩토링 및 보안 강화

## 📌 프로젝트 개요
Outven은 백엔드 구조와 MVC 아키텍처에 익숙해지기 위한 게시판 팀 프로젝트로 시작했습니다.  
처음에는 단순 기능 구현에 초점을 맞췄지만, 개발 후 코드의 구조적 문제와 보안적 허점, 유지보수의 불편함을 직접 경험하면서 전면적인 리팩토링을 진행하게 되었습니다.

2024년 2월, 비전공자 중심 4인 팀 개발 이후 **혼자서 약 1개월간 전면 리팩토링**을 수행했습니다.

## 🎯 리팩토링 목표
- 중복 컨트롤러 통합 및 게시판 분기 처리 전략 적용
- 보안 문제 해결 (세션 처리, 평문 암호, 인증 우회 등)
- Thymeleaf 기반 템플릿 구조 정비
- SRP 기반 서비스/유틸 분리 및 테스트 용이성 향상

## ⚙️ 시스템 아키텍처
- Backend: Java 17, Spring Boot, Spring Security
- DB: Oracle 18c XE, JPA
- Session: Redis + Spring Session
- Template: Thymeleaf
- 배포: Gradle + WAR + 외장 Tomcat (현재는 로컬 전용)

## 🧩 주요 기술 적용 및 개선 사례

### 게시판 구조 개선
- `BoardController` 단일화 + 게시판 `Enum` 기반 분기
- `Controller` → `Service` → `DTO/Entity` → `Repository` 계층화

### 보안 및 인증 개선
- 평문 암호 → BCrypt 해시화
- Spring Security 기반 인증 처리
- Redis 기반 세션 도입 → 중복 로그인 차단
- ControllerAdvice 기반 전역 예외 처리 도입

### 성능 최적화
- `Pageable` + Fetch 전략 개선 → N+1 제거
- 게시판 페이징 응답속도 약 40% 개선 (로컬 1만건 테스트 기준)

## 📈 리팩토링 전후 주요 비교

| 항목 | Before | After |
|------|--------|-------|
| 게시판 구조 | Controller 다중 구현 | BoardController + Enum 통합 |
| 세션 처리 | HttpSession | Spring Session + Redis |
| 인증/보안 | 평문 암호, 세션 우회 | BCrypt, Spring Security, 인증 URL 보호 |
| 템플릿 | Mustache | Thymeleaf Fragment 기반 통합 |
| API 응답 | 비일관 | ResponseEntity 기반 통일 |
| 예외 처리 | try-catch 위주 | ControllerAdvice 전역 처리 |
| 파일 처리 | Controller 직접 처리 | FileService 별도 구성 |
| 댓글 처리 | DAO 방식 | REST API + 비동기 UX 향상 |

## 🧠 회고 및 성장
기존 팀 프로젝트 구조에 한계를 느끼고 직접 개선해보는 과정에서,  
보안, 설계, 유지보수, 성능 등 실무에서 중요하게 보는 관점을 몸소 체험했습니다.  
이 경험은 이후 `별 헤는 밤` 프로젝트에서의 구조 설계 및 인증 기능 확장 기반이 되었습니다.




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
