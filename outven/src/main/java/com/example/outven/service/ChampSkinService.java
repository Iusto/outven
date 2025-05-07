package com.example.outven.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.outven.entity.Champ_skin;
import com.example.outven.entity.Champion;
import com.example.outven.entity.Champskinrate;
import com.example.outven.entity.SkinComment;
import com.example.outven.repository.ChampSkinRateRepository;
import com.example.outven.repository.ChampSkinRepository;
import com.example.outven.repository.ChampionRepository;
import com.example.outven.repository.SkinCommentRepository;

@Service
public class ChampSkinService {

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private ChampSkinRepository champSkinRepository;

    @Autowired
    private ChampSkinRateRepository champSkinRateRepository;

    @Autowired
    private SkinCommentRepository skinCommentRepository;

    // ✅ 챔피언 목록 조회 (페이징 지원)
    public Page<Champion> getChampList(Pageable pageable) {
        return championRepository.findAll(pageable);
    }

    // ✅ 특정 챔피언의 스킨 목록 조회
    public List<Champ_skin> champSkinList(int champCode) {
        return champSkinRepository.findByChampCode(champCode);
    }

    // ✅ 챔피언 스킨 상세 조회
    public Champ_skin champSkinView(int champCode, int skinCode) {
        return champSkinRepository.findByChampCodeAndSkinCode(champCode, skinCode);
    }

    // ✅ 챔피언 스킨 평점 업데이트 (호출 방식 수정)
    public boolean skinRateUpdate(int champCode, int skinCode, double avg) {
        Champ_skin champskin = champSkinRepository.findByChampCodeAndSkinCode(champCode, skinCode);
        if (champskin != null) {
            champskin.setChamp_skin_rate(avg); // 엔티티의 필드명 확인 후 수정
            champSkinRepository.save(champskin);
            return true;
        }
        return false;
    }

    // ✅ 챔피언 스킨 평점 입력
    public void skinRateWrite(Champskinrate champSkinRate) {
        champSkinRateRepository.save(champSkinRate);
    }

    // ✅ 챔피언 스킨 평점 평균 구하기
    public double skinRateAvg(int champCode, int skinCode) {
        return champSkinRateRepository.getAverageRate(champCode, skinCode).orElse(0.0);
    }

    // ✅ 챔피언 스킨 평점 중복 확인
    public boolean skinRateCheck(int champCode, int skinCode, String memberId) {
        return champSkinRateRepository.existsByChampCodeAndSkinCodeAndMemberId(champCode, skinCode, memberId);
    }

    // ✅ 챔피언 스킨 댓글 목록 조회 (페이징 적용)
    public Page<SkinComment> getSkinComments(int skinCode, Pageable pageable) {
        return skinCommentRepository.findBySkinCode(skinCode, pageable);
    }

    // ✅ 챔피언 스킨 댓글 작성 (boolean 반환)
    public boolean skinComWrite(SkinComment comment) {
        try {
            skinCommentRepository.save(comment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ 챔피언 스킨 답글 작성 (boolean 반환)
    public boolean skinRecomment(SkinComment comment) {
        try {
            skinCommentRepository.save(comment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ 챔피언 코드별 댓글 개수 조회
    public long getCountChamp(int champCode) {
        return skinCommentRepository.countByChampCode(champCode);
    }

    // ✅ 스킨 코드별 댓글 개수 조회
    public long getCountSkin(int skinCode) {
        return skinCommentRepository.countBySkinCode(skinCode);
    }
}
