-- 회원
create table member (
    member_id varchar2(30) primary key,    -- 아이디(고유값)
    membername varchar2(30) not null,     -- 이름
    nick_name varchar2(30) not null, -- 별명
    user_password varchar2(50) not null, -- 패스워드
    email varchar2(70) not null, -- 이메일
    phone varchar2(70) not null, -- 전화번호
    birth varchar2(20) not null,    -- 생년월일(Ex.2000-01-30)
    address varchar2(150) not null, -- 주소
    gender varchar2(10) not null, -- 성별(남자면 m 여자면 f)
    user_role varchar2(10) not null,  -- 관리자 유무
    logtime date,  -- 마지막 접속 날짜
    join_time date default sysdate not null, -- 가입일 날짜
    member_level number default 0,         -- 레벨
    member_exp number default 0            -- 경험치 바
);

update member set logtime='24/02/02' where member_id='hong10';
update member set member_exp=90 where member_id='hong10';
select * from member;
commit; 

-- )게시판 관련 테이블

-- 게시판 테이블
    create table board (
    board_num number primary  key, -- 게시글 글번호(고유값)
    board_category varchar2(30) not null, -- 게시판 카테고리(게시판 종류 ex: 칼바람 게시판. 팬아트 게시판, 사건사고 게시판)
    detail_category varchar2(30) not null, -- 디테일 카테고리(게시판에 들어갔을 때 세부 카테고리 ex: 칼바람 나락 => 잡담, 팁, 소식, etc)
    member_id varchar2(30) not null, -- 아이디 (join 사용)
    board_title varchar2(50) not null, -- 게시글제목
    nick_name varchar2(30) not null,  -- 닉네임
    board_content varchar2(4000),     -- 게시글 내용
    board_img varchar2(50),	          -- 게시글 이미지(작성글에 올린 이미지)
    board_logtime date default sysdate, -- 작성일
    board_hit number default 0,        	        -- 조회수(게시글 들어가면 올라가게끔)
    board_recommend number default 0,      --  게시판 추천수(버튼 누르면 증가하게끔)
    board_report_count number default 0     -- 게시판 신고횟수(신고게시판으로 이동)
);

update board set board_recommend = 4 where board_num = 3;
delete from board where board_num = 29;
drop table board purge;
drop sequence board_num;
create sequence board_num nocycle nocache;
select * from board;

commit;

-- 추천
create table recommend(
    recom_num number primary key,   -- 추천 번호
    board_num number,               -- 게시글 번호
    member_id varchar2(30)          -- 아이디
);

-- )게시판 신고 관련 테이블
create table Boardreport (
    report_num number primary key, -- 신고 번호
    member_id varchar2(30),            -- 신고한 사람의 id
    board_num number,                  -- 신고된 글의 번호
    report_category number -- 신고사유(숫자 처리)
);

-- 게시판 댓글
    create table board_comment(
    comment_num number primary key,         -- 댓글 번호
    board_num number,	                       -- 게시글 번호
    member_id varchar2(30) not null,              -- 아이디
    nick_name varchar(30) not null,	           -- 별명
    comment_content varchar2(500),      	-- 댓글 내용
    com_re_ref number,                      -- 회원 그룹 번호(답글용)
    com_re_lev number,                      -- 회원 단계
    com_re_seq number                       -- 회원 글순서
);

-- )챔피언 정보 관련 테이블

-- 챔피언 정보
create table champion(
    champ_code number primary key,   -- 챔피언 고유키
    champ_name varchar(30) not null,          -- 챔피언 이름
    champ_nick_name varchar(50) not null,     -- 챔피언 이명(수식어)
    champ_position varchar(100) not null,     -- 챔피언 포지션(전사, 마법사, 암살자, etc...)
    champ_rate number(5,2),                   -- 챔피언 평점 (챔피언 평점 테이블에 챔피언 점수의 합 / 평점 카운트 갯수) =>(select sum(rate) where champ_code = ? / count where champ code =? 
    champ_image varchar(100),                 -- 챔피언 이미지(픽창 이미지)
    champ_content varchar(1000),              -- 챔피언 설명
    rp number,                          -- 실제재화(캐쉬)
    be number                           -- 인게임 재화
);


-- 챔피언 평점(id, champ_code를 조회했을때 조회되지 않으면 점수를 준다)
create table championRate(
    rate_num number primary key,
    champ_code number not null,               -- 챔피언 번호(현재 페이지의 챔프 코드 값 가져오기)
    member_id VARCHAR2(30) not null,            -- 아이디(로그인 아이디 세션 가져와서 평점 줬는지 안주었는지)
    rate number not null                      -- 점수
);

-- 챔피언 댓글창
create table champion_comment(
    champ_com_num number primary key,        -- 챔피언 댓글 번호
    champ_code number,               -- 챔피언 코드
    member_id varchar2(30),                    -- ID
    nick_name varchar2(30),             -- 회원 별명
    champ_comment varchar2(500),               -- 회원 댓글
    comment_logtime date,
    com_re_ref number,                      -- 회원 그룹 번호(답글용)
    com_re_lev number,                      -- 회원 단계
    com_re_seq number                       -- 회원 글순서
);

-- )챔피언 스킨 관련 테이블

-- 챔피언 스킨
create table champ_skin(
    skin_code number primary key,    -- 스킨 번호(고유값)
    champ_code number,               -- 챔피언 코드
    champ_name varchar(30) not null, -- 챔피언 이름
    champ_skin_name varchar2(50),    -- 스킨 이름
    champ_skin_rate number(5,2) default 0,     -- 챔프 스킨 평점
    champ_image varchar(100),        -- 챔프 이미지 (아이콘)
    champ_skin_img varchar(100),     -- 챔프 스킨 이미지
    skin_rp number default 0         -- 스킨 가격      
);

-- 스킨 평점(id, champ_code, skin_code를 조회했을때 조회되지 않으면 점수를 준다)
create table Champskinrate(
    rate_skin_num number primary key,
    skin_code number not null,
    champ_code number not null,               -- 챔피언 번호(현재 페이지의 챔프 코드 값 가져오기)
    member_id VARCHAR2(30) not null,            -- 아이디(로그인 아이디 세션 가져와서 평점 줬는지 안주었는지)
    rate number not null                      -- 점수
);
commit;
-- 스킨 댓글창 챔피언만 선택시 champ_code 기준으로 스킨을 골랐을때 champ_code와 skin 값이 일치한 데이터들을 조회
create table skin_comment(
    skin_com_num number primary key,        -- 챔피언 댓글 번호
    champ_code number,               -- 챔피언 코드
    skin_code number,               -- 스킨 번호
    member_id varchar2(30),                    -- ID
    nick_name varchar2(30),             -- 회원 별명
    champ_comment varchar2(500),               -- 회원 댓글
    comment_logtime date,
    skin_re_ref number,                      -- 회원 그룹 번호(답글용)
    skin_re_lev number,                      -- 회원 단계
    skin_re_seq number                       -- 회원 글순서
);

-- )블랙리스트 테이블
create table blacklist (
    member_id varchar2(30) primary key,    -- 아이디(고유값)
    membername varchar2(30) not null,     -- 이름
    nick_name varchar2(30) not null, -- 별명
    user_password varchar2(50) not null, -- 패스워드
    email varchar2(70) not null, -- 이메일
    phone varchar2(70) not null, -- 전화번호
    birth varchar2(20) not null,    -- 생년월일(Ex.2000-01-30)
    address varchar2(150) not null, -- 주소
    gender varchar2(10) not null, -- 성별(남자면 m 여자면 f)
    user_role varchar2(10) not null,  -- 관리자 유무
    logtime date,  -- 마지막 로그인 날짜
    jointime date default sysdate not null,  -- 가입날짜
    member_level number default 0,         -- 레벨
    member_exp number default 0            -- 경험치
);