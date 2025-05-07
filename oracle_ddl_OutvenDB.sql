CREATE TABLE board (
    boardnum NUMBER,
    boardcategory VARCHAR2(255),
    detailcategory VARCHAR2(255),
    memberid VARCHAR2(255),
    nickname VARCHAR2(255),
    boardtitle VARCHAR2(255),
    boardcontent VARCHAR2(255),
    boardimg VARCHAR2(255),
    boardhit NUMBER,
    boardrecommend NUMBER,
    boardreportcount NUMBER,
    createdat TIMESTAMP,
    updatedat TIMESTAMP,
    PRIMARY KEY (boardnum)
);

CREATE TABLE board_comment (
    comment_num NUMBER,
    boardnum NUMBER,
    comment_content VARCHAR2(255),
    member_id VARCHAR2(255),
    nick_name VARCHAR2(255),
    comment_logtime TIMESTAMP,
    recommend NUMBER,
    parentcommentnum NUMBER,
    PRIMARY KEY (comment_num)
);

CREATE TABLE boardreport (
    reportid NUMBER PRIMARY KEY,
    boardnum NUMBER,
    reporterid VARCHAR2(255),
    reason VARCHAR2(255),
    reportdate DATE
);

CREATE TABLE champ_skin (
    skincode NUMBER,
    champcode NUMBER,
    champ_skin_name VARCHAR2(255),
    champ_skin_rate FLOAT,
    champ_icon_img VARCHAR2(255),
    champ_skin_img VARCHAR2(255),
    skin_rp NUMBER,
    skin_logtime DATE
);

CREATE TABLE champion (
    champ_code NUMBER,
    champ_name VARCHAR2(255),
    champ_nick_name VARCHAR2(255),
    champ_position VARCHAR2(255),
    champ_rate FLOAT,
    champ_image VARCHAR2(255),
    champ_content VARCHAR2(255),
    rp NUMBER,
    be NUMBER
);

CREATE TABLE champion_comment (
    champ_com_num NUMBER,
    champcode NUMBER,
    member_id VARCHAR2(255),
    nick_name VARCHAR2(255),
    champ_comment VARCHAR2(255),
    comment_logtime DATE,
    com_re_ref NUMBER,
    com_re_lev NUMBER,
    com_re_seq NUMBER,
    com_judge CHAR(1),
    PRIMARY KEY (champ_com_num)
);

CREATE TABLE championrate (
    id NUMBER,
    champcode NUMBER,
    rate NUMBER,
    memberid VARCHAR2(255),
    PRIMARY KEY (id)
);

CREATE TABLE champskinrate (
    id NUMBER,
    champcode NUMBER,
    skincode NUMBER,
    memberid VARCHAR2(255),
    rate NUMBER,
    PRIMARY KEY (id)
);

CREATE TABLE commentrecommend (
    id NUMBER,
    commentnum NUMBER,
    memberid VARCHAR2(255),
    PRIMARY KEY (id)
);

CREATE TABLE join_comskin (
    skin_com_num NUMBER,
    champ_code NUMBER,
    skin_code NUMBER,
    member_id VARCHAR2(255),
    nick_name VARCHAR2(255),
    champskin_com VARCHAR2(255),
    comment_logtime DATE,
    champ_icon_img VARCHAR2(255),
    champ_skin_rate FLOAT,
    champ_skin_name VARCHAR2(255),
    PRIMARY KEY (skin_com_num)
);

CREATE TABLE member (
    memberid VARCHAR2(255),
    membername VARCHAR2(255),
    nickname VARCHAR2(255),
    password VARCHAR2(255),
    email VARCHAR2(255),
    phone VARCHAR2(255),
    address VARCHAR2(255),
    gender VARCHAR2(255),
    userrole VARCHAR2(255),
    jointime DATE,
    birth VARCHAR2(255),
    member_level VARCHAR2(255),
    member_exp VARCHAR2(255),
    banreason VARCHAR2(255),
    banuntil TIMESTAMP,
    PRIMARY KEY (memberid)
);

CREATE TABLE recommend (
    recom_num NUMBER,
    board_num NUMBER,
    member_id VARCHAR2(255),
    PRIMARY KEY (recom_num)
);

CREATE TABLE skincomment (
    skin_com_num NUMBER,
    champcode NUMBER,
    skincode NUMBER,
    member_id VARCHAR2(255),
    nick_name VARCHAR2(255),
    champskin_com VARCHAR2(255),
    comment_logtime DATE,
    skin_re_ref NUMBER,
    skin_re_lev NUMBER,
    skin_re_seq NUMBER,
    com_judge CHAR(1),
    PRIMARY KEY (skin_com_num)
);

commit