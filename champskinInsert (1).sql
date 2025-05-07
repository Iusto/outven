-- è�Ǿ� ��Ų
create table champ_skin(
    skin_code number primary key,    -- ��Ų ��ȣ(������)
    champ_code number,               -- è�Ǿ� �ڵ�
    champ_skin_name varchar2(50),    -- ��Ų �̸�
    champ_skin_rate number(5,2) default 0,     -- è�� ��Ų ����
    champ_icon_img varchar2(100),        -- è�� �̹��� (������)
    champ_skin_img varchar2(100),     -- è�� ��Ų �̹���
    skin_rp number default 0,         -- ��Ų ����
    skin_logtime date                 -- ������
);
drop table champ_skin purge;
commit;
select * from tab;
select * from champ_skin;

-- ������ ��ü ����
create sequence champ_sk_seq nocache nocycle;
drop sequence champ_sk_seq;
select * from champ_skin;
select * from champ_skin order by champ_code;

select * from champ_skin where champ_code=1 and skin_code=170;
commit;
-- ����
delete from champ_skin;

-- join
select * from champion a inner join champ_skin b on
a.champ_code= b.champ_code;

select b.skin_code, b.champ_code, b.champ_skin_name, b.champ_skin_rate,
b.champ_icon_img, b.champ_skin_img, b.skin_rp, b.skin_logtime
from champion a inner join champ_skin b on
a.champ_code= b.champ_code where a.champ_code=1 and b.champ_code =1;

select * from champ_skin where champ_code = 1;


-- è�Ǿ� ��Ų(�߰���Ų)
insert into champ_skin values
(champ_sk_seq.nextval, 1,'Ư���� ����', 0, 'Garen_square1.jpg', 'Garen_1.jpg', 520 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, 1,'�縷Ư���� ����', 0, 'Garen_square2.jpg', 'Garen_2.jpg', 520 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, 1,'������ ��� ����', 0, 'Garen_square3.jpg', 'Garen_3.jpg', 975 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, 1,'����� ����', 0, 'Garen_square4.jpg', 'Garen_4.jpg', 750 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, 1,'��ö ���� ����', 0, 'Garen_square5.jpg', 'Garen_5.jpg', 1350 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, 1,'�ż��� ����', 0, 'Garen_square6.jpg', 'Garen_6.jpg', 1820 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, 1,'�����þ� ���¹� ����', 0, 'Garen_square7.jpg', 'Garen_7.jpg', 1350 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, 1,'��ī �ﱹ ����', 0, 'Garen_square8.jpg', 'Garen_8.jpg', 1350 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, 1,'��������б� ����', 0, 'Garen_square9.jpg', 'Garen_9.jpg', 1350,sysdate);


-- è�Ǿ� ��Ų �Է�(�⺻��Ų)

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Garen_square.jpg', 'Garen_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Galio_square.jpg', 'Galio_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���÷�ũ', 0, 'Gangplank_square.jpg', 'Gangplank_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �׶󰡽�', 0, 'Gragas_square.jpg', 'Gragas_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �׷��̺���', 0, 'Graves_square.jpg', 'Graves_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Gwen_square.jpg', 'Gwen_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Gnar_square.jpg', 'Gnar_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Nami_square.jpg', 'Nami_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Nasus_square.jpg', 'Nasus_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���Ǹ�', 0, 'Naafiri_square.jpg', 'Naafiri_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ��ƿ����', 0, 'Nautilus_square.jpg', 'Nautilus_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Nocturne_square.jpg', 'Nocturne_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������ ������', 0, 'Nunu_square.jpg', 'Nunu_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ϴ޸�', 0, 'Nidalee_square.jpg', 'Nidalee_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Neeko_square.jpg', 'Neeko_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �Ҷ�', 0, 'Nilah_square.jpg', 'Nilah_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ٸ��콺', 0, 'Darius_square.jpg', 'Darius_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���ֳ̾�', 0, 'Diana_square.jpg', 'Diana_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �巹�̺�', 0, 'Draven_square.jpg', 'Draven_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Ryze_square.jpg', 'Ryze_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ��ĭ', 0, 'Rakan_square.jpg', 'Rakan_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���ӽ�', 0, 'Rammus_square.jpg', 'Rammus_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Lux_square.jpg', 'Lux_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Rumble_square.jpg', 'Rumble_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����Ÿ �۶�ũ', 0, 'Reneta_square.jpg', 'Reneta_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Renekton_square.jpg', 'Renekton_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Leona_square.jpg', 'Leona_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Reksai_square.jpg', 'Reksai_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ��', 0, 'Rell_square.jpg', 'Rell_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Rengar_square.jpg', 'Rengar_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ��þ�', 0, 'Lucian_square.jpg', 'Lucian_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���', 0, 'Lulu_square.jpg', 'Lulu_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �����', 0, 'LeBlanc_square.jpg', 'LeBlanc_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'LeeSin_square.jpg', 'LeeSin_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Riven_square.jpg', 'Riven_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Lissandra_square.jpg', 'Lissandra_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Lillia_square.jpg', 'Lillia_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������ ��', 0, 'MasterYi_square.jpg', 'MasterYi_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����ī��', 0, 'Maokai_square.jpg', 'Maokai_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Malzahar_square.jpg', 'Malzahar_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������Ʈ', 0, 'Malphite_square.jpg', 'Malphite_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ��ī����', 0, 'Modekaiser_square.jpg', 'Modekaiser_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �𸣰���', 0, 'Morgana_square.jpg', 'Morgana_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���� �ڻ�', 0, 'Mundo_square.jpg', 'Mundo_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �̽� ����', 0, 'MsFortune_square.jpg', 'MsFortune_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �и���', 0, 'Milio_square.jpg', 'Milio_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ٵ�', 0, 'Bard_square.jpg', 'Bard_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ٷ罺', 0, 'Varus_square.jpg', 'Varus_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Vi_square.jpg', 'Vi_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���̰�', 0, 'Veigar_square.jpg', 'Veigar_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Vayne_square.jpg', 'Vayne_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Vex_square.jpg', 'Vex_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Belveth_square.jpg', 'Belveth_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Velkoz_square.jpg', 'Velkoz_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ��������', 0, 'Volibear_square.jpg', 'Volibear_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Braum_square.jpg', 'Braum_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����̾�', 0, 'Briar_square.jpg', 'Briar_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �귣��', 0, 'Brand_square.jpg', 'Brand_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����̸�', 0, 'Vladimir_square.jpg', 'Vladimir_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����ũ��ũ', 0, 'Blitzcrank_square.jpg', 'Blitzcrank_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �񿡰�', 0, 'Viego_square.jpg', 'Viego_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���丣', 0, 'Viktor_square.jpg', 'Viktor_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ǻ�', 0, 'Poppy_square.jpg', 'Poppy_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ��̶�', 0, 'Samira_square.jpg', 'Samira_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���̿�', 0, 'Sion_square.jpg', 'Sion_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���Ϸ���', 0, 'Sylus_square.jpg', 'Sylus_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Shaco_square.jpg', 'Shaco_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Senna_square.jpg', 'Senna_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Seraphine_square.jpg', 'Seraphine_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���־ƴ�', 0, 'Sejuani_square.jpg', 'Sejuani_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ��Ʈ', 0, 'Sett_square.jpg', 'Sett_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ҳ�', 0, 'Sona_square.jpg', 'Sona_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �Ҷ�ī', 0, 'Soraka_square.jpg', 'Soraka_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ��', 0, 'Shen_square.jpg', 'Shen_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���ٳ�', 0, 'Shybana_square.jpg', 'Shybana_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Smolder_square.jpg', 'Smolder_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Swain_square.jpg', 'Swain_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ��ī��', 0, 'Skarner_square.jpg', 'Skarner_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ú�', 0, 'Sivir_square.jpg', 'Sivir_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �� ¥��', 0, 'XinZhao_square.jpg', 'XinZhao_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ŵ��', 0, 'Syndra_square.jpg', 'Syndra_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Singed_square.jpg', 'Singed_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Thresh_square.jpg', 'Thresh_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �Ƹ�', 0, 'Ahri_square.jpg', 'Ahri_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ƹ���', 0, 'Amumu_square.jpg', 'Amumu_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ƿ췼���� ��', 0, 'Aurelion_square.jpg', 'Aurelion_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���̹�', 0, 'Ivern_square.jpg', 'Ivern_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Azir_square.jpg', 'Azir_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ��Į��', 0, 'Akali_square.jpg', 'Akali_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ��ũ��', 0, 'Akshan_square.jpg', 'Akshan_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ��Ʈ�Ͻ�', 0, 'Aatrox_square.jpg', 'Aatrox_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���縮����', 0, 'Aphelios_square.jpg', 'Aphelios_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �˸���Ÿ', 0, 'Alistar_square.jpg', 'Alistar_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ִ�', 0, 'Annie_square.jpg', 'Annie_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ִϺ��', 0, 'Anivia_square.jpg', 'Anivia_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ֽ�', 0, 'Ashe_square.jpg', 'Ashe_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �߽���', 0, 'Yasuo_square.jpg', 'Yasuo_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Ekko_square.jpg', 'Ekko_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ٸ���', 0, 'Elise_square.jpg', 'Elise_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Wukong_square.jpg', 'Wukong_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Ornn_square.jpg', 'Ornn_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �����Ƴ�', 0, 'Orianna_square.jpg', 'Orianna_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ö���', 0, 'Olaf_square.jpg', 'Olaf_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���', 0, 'Yone_square.jpg', 'Yone_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �丯', 0, 'Yorick_square.jpg', 'Yorick_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���', 0, 'Udyr_square.jpg', 'Udyr_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �츣��', 0, 'Urgot_square.jpg', 'Urgot_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Warwick_square.jpg', 'Warwick_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Yuumi_square.jpg', 'Yuumi_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �̷�����', 0, 'Irelia_square.jpg', 'Irelia_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �̺�', 0, 'Evelynn_square.jpg', 'Evelynn_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �����', 0, 'Ezreal_square.jpg', 'Ezreal_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �϶����', 0, 'Illaoi_square.jpg', 'Illaoi_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ڸ��� 4��', 0, 'Jarvan_square.jpg', 'Jarvan_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ھ�', 0, 'Xayah_square.jpg', 'Xayah_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���̶�', 0, 'Zyra_square.jpg', 'Zyra_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ��ũ', 0, 'Zac_square.jpg', 'Zac_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ܳ�', 0, 'Janna_square.jpg', 'Janna_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �轺', 0, 'Jax_square.jpg', 'Jax_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Zed_square.jpg', 'Zed_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Xerath_square.jpg', 'Xerath_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Zeri_square.jpg', 'Zeri_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���̽�', 0, 'Jayce_square.jpg', 'Jayce_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Zoe_square.jpg', 'Zoe_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Ziggs_square.jpg', 'Ziggs_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ��', 0, 'Jhin_square.jpg', 'Jhin_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ������', 0, 'Zilean_square.jpg', 'Zilean_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ¡ũ��', 0, 'Jinx_square.jpg', 'Jinx_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ʰ���', 0, 'Chogath_square.jpg', 'Chogath_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ī����', 0, 'Karma_square.jpg', 'Karma_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ī��', 0, 'Camille_square.jpg', 'Camille_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ī���', 0, 'Kassadin_square.jpg', 'Kassadin_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ī����', 0, 'Karthus_square.jpg', 'Karthus_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ī�ÿ����', 0, 'Cassiopeia_square.jpg', 'Cassiopeia_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ī�̻�', 0, 'Kaisa_square.jpg', 'Kaisa_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ī����', 0, 'Khazix_square.jpg', 'Khazix_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų īŸ����', 0, 'Katarina_square.jpg', 'Katarina_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų Į����Ÿ', 0, 'Kalista_square.jpg', 'Kalista_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ɳ�', 0, 'Kennen_square.jpg', 'Kennen_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����Ʋ��', 0, 'Caitlyn_square.jpg', 'Caitlyn_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Kayn_square.jpg', 'Kayn_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Kayle_square.jpg', 'Kayle_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ڱ׸�', 0, 'Kogmaw_square.jpg', 'Kogmaw_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ڸ�Ű', 0, 'Corki_square.jpg', 'Corki_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ��', 0, 'Quinn_square.jpg', 'Quinn_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ũ����', 0, 'Ksante_square.jpg', 'Ksante_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų Ŭ����', 0, 'Kled_square.jpg', 'Kled_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų Ű�Ƴ�', 0, 'Qiyana_square.jpg', 'Qiyana_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų Ų�巹��', 0, 'Kindred_square.jpg', 'Kindred_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų Ÿ��', 0, 'Taric_square.jpg', 'Taric_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų Ż��', 0, 'Talon_square.jpg', 'Talon_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų Ż����', 0, 'Taliyah_square.jpg', 'Taliyah_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų Ž ��ġ', 0, 'Tahmkench_square.jpg', 'Tahmkench_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų Ʈ����', 0, 'Trundle_square.jpg', 'Trundle_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų Ʈ����Ÿ��', 0, 'Tristana_square.jpg', 'Tristana_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų Ʈ���ٹ̾�', 0, 'Tryndamere_square.jpg', 'Tryndamere_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų Ʈ����Ƽ�� ����Ʈ', 0, 'Twistedfate_square.jpg', 'Twistedfate_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų Ʈ��ġ', 0, 'Twitch_square.jpg', 'Twitch_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų Ƽ��', 0, 'Teemo_square.jpg', 'Teemo_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����ũ', 0, 'Pyke_square.jpg', 'Pyke_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���׿�', 0, 'Pantheon_square.jpg', 'Pantheon_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ǵ齺ƽ', 0, 'Fiddlesticks_square.jpg', 'Fiddlesticks_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �ǿ���', 0, 'Fiora_square.jpg', 'Fiora_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ����', 0, 'Fizz_square.jpg', 'Fizz_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ���̸ӵ���', 0, 'Heimerdinger_square.jpg', 'Heimerdinger_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų ��ī��', 0, 'Hecarim_square.jpg', 'Hecarim_0.jpg',0 ,sysdate);

insert into champ_skin values
(champ_sk_seq.nextval, champ_sk_seq.currval,'�⺻ ��Ų �����', 0, 'Hwei_square.jpg', 'Hwei_0.jpg',0 ,sysdate);



