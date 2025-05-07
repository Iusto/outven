-- 챔피언 스킨
create table champ_skin(
    skin_code number primary key,    -- 스킨 번호(고유값)
    champ_code number,               -- 챔피언 코드
    champ_skin_name varchar2(50),    -- 스킨 이름
    champ_skin_rate number(5,2) default 0,     -- 챔프 스킨 평점
    champ_icon_img varchar2(100),        -- 챔프 이미지 (아이콘)
    champ_skin_img varchar2(100),     -- 챔프 스킨 이미지
    skin_rp number default 0,         -- 스킨 가격
    skin_logtime date                 -- 생성일
);
drop table champ_skin purge;
commit;
select * from tab;
select * from champ_skin;

-- 시퀀스 객체 생성
create sequence champ_sk_seq nocache nocycle;
drop sequence champ_sk_seq;
select * from champ_skin;
select * from champ_skin order by champ_code;

select * from champ_skin where champ_code=1 and skin_code=170;
commit;
-- 삭제
delete from champ_skin;

-- join
select * from champion a inner join champ_skin b on
a.champ_code= b.champ_code;

select b.skin_code, b.champ_code, b.champ_skin_name, b.champ_skin_rate,
b.champ_icon_img, b.champ_skin_img, b.skin_rp, b.skin_logtime
from champion a inner join champ_skin b on
a.champ_code= b.champ_code where a.champ_code=1 and b.champ_code =1;

select * from champ_skin where champ_code = 1;


-- 챔피언 스킨(추가스킨)
insert into champ_skin values
(champ_sk_seq.nextval, 1,'특공대 가렌', 0, 'Garen_square1.jpg', 'Garen_1.jpg', 520 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, 1,'사막특전사 가렌', 0, 'Garen_square2.jpg', 'Garen_2.jpg', 520 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, 1,'공포의 기사 가렌', 0, 'Garen_square3.jpg', 'Garen_3.jpg', 975 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, 1,'방랑자 가렌', 0, 'Garen_square4.jpg', 'Garen_4.jpg', 750 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, 1,'강철 군단 가렌', 0, 'Garen_square5.jpg', 'Garen_5.jpg', 1350 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, 1,'신성왕 가렌', 0, 'Garen_square6.jpg', 'Garen_6.jpg', 1820 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, 1,'데마시아 강력반 가렌', 0, 'Garen_square7.jpg', 'Garen_7.jpg', 1350 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, 1,'메카 삼국 가렌', 0, 'Garen_square8.jpg', 'Garen_8.jpg', 1350 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, 1,'전투사관학교 가렌', 0, 'Garen_square9.jpg', 'Garen_9.jpg', 1350,sysdate);


-- 챔피언 스킨 입력(기본스킨)

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 가렌', 0, 'Garen_square.jpg', 'Garen_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 갈리오', 0, 'Galio_square.jpg', 'Galio_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 갱플랭크', 0, 'Gangplank_square.jpg', 'Gangplank_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 그라가스', 0, 'Gragas_square.jpg', 'Gragas_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 그레이브즈', 0, 'Graves_square.jpg', 'Graves_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 그웬', 0, 'Gwen_square.jpg', 'Gwen_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 나르', 0, 'Gnar_square.jpg', 'Gnar_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 나미', 0, 'Nami_square.jpg', 'Nami_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 나서스', 0, 'Nasus_square.jpg', 'Nasus_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 나피리', 0, 'Naafiri_square.jpg', 'Naafiri_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 노틸러스', 0, 'Nautilus_square.jpg', 'Nautilus_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 녹턴', 0, 'Nocturne_square.jpg', 'Nocturne_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 누누와 윌럼프', 0, 'Nunu_square.jpg', 'Nunu_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 니달리', 0, 'Nidalee_square.jpg', 'Nidalee_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 니코', 0, 'Neeko_square.jpg', 'Neeko_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 닐라', 0, 'Nilah_square.jpg', 'Nilah_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 다리우스', 0, 'Darius_square.jpg', 'Darius_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 다이애나', 0, 'Diana_square.jpg', 'Diana_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 드레이븐', 0, 'Draven_square.jpg', 'Draven_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 라이즈', 0, 'Ryze_square.jpg', 'Ryze_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 라칸', 0, 'Rakan_square.jpg', 'Rakan_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 람머스', 0, 'Rammus_square.jpg', 'Rammus_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 럭스', 0, 'Lux_square.jpg', 'Lux_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 럼블', 0, 'Rumble_square.jpg', 'Rumble_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 레나타 글라스크', 0, 'Reneta_square.jpg', 'Reneta_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 레넥톤', 0, 'Renekton_square.jpg', 'Renekton_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 레오나', 0, 'Leona_square.jpg', 'Leona_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 렉사이', 0, 'Reksai_square.jpg', 'Reksai_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 렐', 0, 'Rell_square.jpg', 'Rell_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 렝가', 0, 'Rengar_square.jpg', 'Rengar_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 루시안', 0, 'Lucian_square.jpg', 'Lucian_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 룰루', 0, 'Lulu_square.jpg', 'Lulu_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 르블랑', 0, 'LeBlanc_square.jpg', 'LeBlanc_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 리신', 0, 'LeeSin_square.jpg', 'LeeSin_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 리븐', 0, 'Riven_square.jpg', 'Riven_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 리산드라', 0, 'Lissandra_square.jpg', 'Lissandra_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 릴리아', 0, 'Lillia_square.jpg', 'Lillia_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 마스터 이', 0, 'MasterYi_square.jpg', 'MasterYi_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 마오카이', 0, 'Maokai_square.jpg', 'Maokai_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 말자하', 0, 'Malzahar_square.jpg', 'Malzahar_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 말파이트', 0, 'Malphite_square.jpg', 'Malphite_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 모데카이저', 0, 'Modekaiser_square.jpg', 'Modekaiser_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 모르가나', 0, 'Morgana_square.jpg', 'Morgana_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 문도 박사', 0, 'Mundo_square.jpg', 'Mundo_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 미스 포츈', 0, 'MsFortune_square.jpg', 'MsFortune_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 밀리오', 0, 'Milio_square.jpg', 'Milio_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 바드', 0, 'Bard_square.jpg', 'Bard_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 바루스', 0, 'Varus_square.jpg', 'Varus_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 바이', 0, 'Vi_square.jpg', 'Vi_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 베이가', 0, 'Veigar_square.jpg', 'Veigar_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 베인', 0, 'Vayne_square.jpg', 'Vayne_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 벡스', 0, 'Vex_square.jpg', 'Vex_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 벨베스', 0, 'Belveth_square.jpg', 'Belveth_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 벨코즈', 0, 'Velkoz_square.jpg', 'Velkoz_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 볼리베어', 0, 'Volibear_square.jpg', 'Volibear_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 브라움', 0, 'Braum_square.jpg', 'Braum_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 브라이어', 0, 'Briar_square.jpg', 'Briar_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 브랜드', 0, 'Brand_square.jpg', 'Brand_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 블라디미르', 0, 'Vladimir_square.jpg', 'Vladimir_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 블리츠크랭크', 0, 'Blitzcrank_square.jpg', 'Blitzcrank_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 비에고', 0, 'Viego_square.jpg', 'Viego_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 빅토르', 0, 'Viktor_square.jpg', 'Viktor_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 뽀삐', 0, 'Poppy_square.jpg', 'Poppy_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 사미라', 0, 'Samira_square.jpg', 'Samira_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 사이온', 0, 'Sion_square.jpg', 'Sion_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 사일러스', 0, 'Sylus_square.jpg', 'Sylus_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 샤코', 0, 'Shaco_square.jpg', 'Shaco_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 세나', 0, 'Senna_square.jpg', 'Senna_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 세라핀', 0, 'Seraphine_square.jpg', 'Seraphine_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 세주아니', 0, 'Sejuani_square.jpg', 'Sejuani_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 세트', 0, 'Sett_square.jpg', 'Sett_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 소나', 0, 'Sona_square.jpg', 'Sona_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 소라카', 0, 'Soraka_square.jpg', 'Soraka_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 쉔', 0, 'Shen_square.jpg', 'Shen_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 쉬바나', 0, 'Shybana_square.jpg', 'Shybana_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 스몰더', 0, 'Smolder_square.jpg', 'Smolder_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 스웨인', 0, 'Swain_square.jpg', 'Swain_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 스카너', 0, 'Skarner_square.jpg', 'Skarner_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 시비르', 0, 'Sivir_square.jpg', 'Sivir_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 신 짜오', 0, 'XinZhao_square.jpg', 'XinZhao_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 신드라', 0, 'Syndra_square.jpg', 'Syndra_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 신지드', 0, 'Singed_square.jpg', 'Singed_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 쓰레쉬', 0, 'Thresh_square.jpg', 'Thresh_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 아리', 0, 'Ahri_square.jpg', 'Ahri_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 아무무', 0, 'Amumu_square.jpg', 'Amumu_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 아우렐리온 솔', 0, 'Aurelion_square.jpg', 'Aurelion_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 아이번', 0, 'Ivern_square.jpg', 'Ivern_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 아지르', 0, 'Azir_square.jpg', 'Azir_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 아칼리', 0, 'Akali_square.jpg', 'Akali_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 아크샨', 0, 'Akshan_square.jpg', 'Akshan_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 아트록스', 0, 'Aatrox_square.jpg', 'Aatrox_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 아펠리오스', 0, 'Aphelios_square.jpg', 'Aphelios_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 알리스타', 0, 'Alistar_square.jpg', 'Alistar_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 애니', 0, 'Annie_square.jpg', 'Annie_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 애니비아', 0, 'Anivia_square.jpg', 'Anivia_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 애쉬', 0, 'Ashe_square.jpg', 'Ashe_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 야스오', 0, 'Yasuo_square.jpg', 'Yasuo_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 에코', 0, 'Ekko_square.jpg', 'Ekko_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 앨리스', 0, 'Elise_square.jpg', 'Elise_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 오공', 0, 'Wukong_square.jpg', 'Wukong_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 오른', 0, 'Ornn_square.jpg', 'Ornn_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 오리아나', 0, 'Orianna_square.jpg', 'Orianna_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 올라프', 0, 'Olaf_square.jpg', 'Olaf_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 요네', 0, 'Yone_square.jpg', 'Yone_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 요릭', 0, 'Yorick_square.jpg', 'Yorick_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 우디르', 0, 'Udyr_square.jpg', 'Udyr_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 우르곳', 0, 'Urgot_square.jpg', 'Urgot_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 워윅', 0, 'Warwick_square.jpg', 'Warwick_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 유미', 0, 'Yuumi_square.jpg', 'Yuumi_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 이렐리아', 0, 'Irelia_square.jpg', 'Irelia_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 이블린', 0, 'Evelynn_square.jpg', 'Evelynn_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 이즈리얼', 0, 'Ezreal_square.jpg', 'Ezreal_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 일라오이', 0, 'Illaoi_square.jpg', 'Illaoi_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 자르반 4세', 0, 'Jarvan_square.jpg', 'Jarvan_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 자야', 0, 'Xayah_square.jpg', 'Xayah_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 자이라', 0, 'Zyra_square.jpg', 'Zyra_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 자크', 0, 'Zac_square.jpg', 'Zac_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 잔나', 0, 'Janna_square.jpg', 'Janna_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 잭스', 0, 'Jax_square.jpg', 'Jax_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 제드', 0, 'Zed_square.jpg', 'Zed_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 제라스', 0, 'Xerath_square.jpg', 'Xerath_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 제리', 0, 'Zeri_square.jpg', 'Zeri_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 제이스', 0, 'Jayce_square.jpg', 'Jayce_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 조이', 0, 'Zoe_square.jpg', 'Zoe_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 직스', 0, 'Ziggs_square.jpg', 'Ziggs_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 진', 0, 'Jhin_square.jpg', 'Jhin_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 질리언', 0, 'Zilean_square.jpg', 'Zilean_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 징크스', 0, 'Jinx_square.jpg', 'Jinx_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 초가스', 0, 'Chogath_square.jpg', 'Chogath_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 카르마', 0, 'Karma_square.jpg', 'Karma_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 카밀', 0, 'Camille_square.jpg', 'Camille_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 카사딘', 0, 'Kassadin_square.jpg', 'Kassadin_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 카서스', 0, 'Karthus_square.jpg', 'Karthus_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 카시오페아', 0, 'Cassiopeia_square.jpg', 'Cassiopeia_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 카이사', 0, 'Kaisa_square.jpg', 'Kaisa_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 카직스', 0, 'Khazix_square.jpg', 'Khazix_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 카타리나', 0, 'Katarina_square.jpg', 'Katarina_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 칼리스타', 0, 'Kalista_square.jpg', 'Kalista_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 케넨', 0, 'Kennen_square.jpg', 'Kennen_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 케이틀린', 0, 'Caitlyn_square.jpg', 'Caitlyn_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 케인', 0, 'Kayn_square.jpg', 'Kayn_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 케일', 0, 'Kayle_square.jpg', 'Kayle_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 코그모', 0, 'Kogmaw_square.jpg', 'Kogmaw_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 코르키', 0, 'Corki_square.jpg', 'Corki_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 퀸', 0, 'Quinn_square.jpg', 'Quinn_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 크산테', 0, 'Ksante_square.jpg', 'Ksante_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 클레드', 0, 'Kled_square.jpg', 'Kled_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 키아나', 0, 'Qiyana_square.jpg', 'Qiyana_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 킨드레드', 0, 'Kindred_square.jpg', 'Kindred_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 타릭', 0, 'Taric_square.jpg', 'Taric_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 탈론', 0, 'Talon_square.jpg', 'Talon_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 탈리야', 0, 'Taliyah_square.jpg', 'Taliyah_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 탐 켄치', 0, 'Tahmkench_square.jpg', 'Tahmkench_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 트런들', 0, 'Trundle_square.jpg', 'Trundle_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 트리스타나', 0, 'Tristana_square.jpg', 'Tristana_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 트린다미어', 0, 'Tryndamere_square.jpg', 'Tryndamere_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 트위스티드 페이트', 0, 'Twistedfate_square.jpg', 'Twistedfate_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 트위치', 0, 'Twitch_square.jpg', 'Twitch_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 티모', 0, 'Teemo_square.jpg', 'Teemo_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 파이크', 0, 'Pyke_square.jpg', 'Pyke_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 판테온', 0, 'Pantheon_square.jpg', 'Pantheon_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 피들스틱', 0, 'Fiddlesticks_square.jpg', 'Fiddlesticks_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 피오라', 0, 'Fiora_square.jpg', 'Fiora_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 피즈', 0, 'Fizz_square.jpg', 'Fizz_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 하이머딩거', 0, 'Heimerdinger_square.jpg', 'Heimerdinger_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 헤카림', 0, 'Hecarim_square.jpg', 'Hecarim_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'기본 스킨 흐웨이', 0, 'Hwei_square.jpg', 'Hwei_0.jpg',0 ,sysdate);



