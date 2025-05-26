📝 Outven - 게시판 아키텍처 리팩토링 및 보안 강화 프로젝트
📌 프로젝트 개요
Outven은 백엔드 구조와 MVC 아키텍처에 익숙해지기 위한 개인 게시판 프로젝트로 시작했습니다.
처음에는 단순 기능 구현에 초점을 맞췄지만, 개발 후 코드의 구조적 문제와 보안적 허점, 유지보수의 불편함을 직접 경험하면서 전면적인 리팩토링을 진행하게 되었습니다.

🎯 리팩토링 목표
복잡하고 중복된 게시판 컨트롤러 통합

보안 문제 해결 (세션, 평문 비밀번호, 인증 우회 등)

아키텍처 및 배포 구조 개선

성능 개선 (N+1 제거, Fetch 전략 최적화)

⚙️ 시스템 아키텍처
Backend: Java 17, Spring Boot

Template: Thymeleaf (기존 Mustache → 전환)

DB: Oracle 18c XE

Session: Spring Session + Redis (부하테스트 기반 도입)

보안: Spring Security + BCrypt 해시 암호화

배포: Gradle 기반 WAR 패키징, 외부 톰캣 (현재는 로컬 한정)

📁 폴더 구조 예시
pgsql
복사
편집
📦outven
 ┣ 📂controller
 ┣ 📂service
 ┣ 📂repository
 ┣ 📂dto
 ┣ 📂config
 ┣ 📂security
 ┣ 📂exception
 ┗ 📜application.yml
🧩 주요 개선 사항
📍 1. 게시판 컨트롤러 통합
자유게시판, 이미지게시판, 사고게시판 등 개별 컨트롤러 → BoardController 단일화

게시판 타입은 Enum 전략 기반 분기 처리

유지보수성과 가독성 대폭 향상

📍 2. 보안 구조 재설계
평문 저장 → BCrypt 기반 해싱 암호화

Spring Security 도입: 인증 우회 문제 제거

비로그인 시 수정/삭제 링크 접근 가능 문제 해결

일반 세션 → Redis 기반 Spring Session으로 전환

중복 로그인 이슈 직접 발견 후 개선

📍 3. 퍼포먼스 최적화
JPA Fetch 전략 조정으로 N+1 제거

Pageable 기반 페이지네이션 도입

더미 데이터(10,000건)로 로컬 환경 테스트 → 평균 응답 속도 40% 향상

🧪 테스트
단위 테스트: JUnit 기반 작성

통합 테스트 일부 작성

향후 Mockito 기반 시나리오 테스트 강화 예정

📦 배포
Gradle 기반 WAR 패키징

외장 톰캣 배포를 고려한 구조 설계

현재는 로컬 환경 중심 운영 (WAR 배포의 한계 인식)

🧠 회고
개발 당시엔 몰랐던 보안적 허점들을 실제로 발견하면서 Spring Security와 세션 구조의 중요성을 체감함

컨트롤러 리팩토링을 통해 가독성과 유지보수성이 극적으로 향상됨

테스트 코드와 배포 자동화의 부재는 현재 관점에서 아쉬운 부분이며, 차기 프로젝트(별 헤는 밤)에서 이를 중점적으로 개선하고 있음

💬 기술 스택
Java 17, Spring Boot, JPA, Spring Security, Redis, Thymeleaf

Oracle 18c XE, Gradle, WAR, GitHub
