package com.example.outven.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.outven.entity.Champion;
import com.example.outven.entity.Champion_comment;
import com.example.outven.entity.Championrate;
import com.example.outven.repository.ChampionRepository;
import com.example.outven.repository.ChampionRateRepository;
import com.example.outven.repository.ChampionCommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ChampionService {

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private ChampionRateRepository championRateRepository;

    @Autowired
    private ChampionCommentRepository championCommentRepository;


    // 🔹 챔피언 상세 조회
    public Champion championView(int championCode) {
        return championRepository.findById(championCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 챔피언이 존재하지 않습니다: " + championCode));
    }

    // 🔹 챔피언 평점 입력 (중복 방지)
    public boolean champRateWrite(Championrate championRate) {
        try {
            if (champRateCheck(championRate.getChampCode(), championRate.getMemberId())) {
                return false; // 중복 방지
            }
            championRateRepository.save(championRate);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // 오류 로그 출력
            return false;
        }
    }




    // 🔹 챔피언 평균 평점 계산
    public double champRateAvg(int champCode) {
        return championRateRepository.getAverageRate(champCode).orElse(0.0);
    }

    // 🔹 평점이 이미 존재하는지 확인
    public boolean champRateCheck(int champCode, String memberId) {
        return championRateRepository.existsByChampCodeAndMemberId(champCode, memberId);
    }


    // 🔹 챔피언 평점 업데이트
    public boolean champRateUpdate(int champCode, double avg) {
        Optional<Champion> optionalChampion = championRepository.findById(champCode);
        if (optionalChampion.isPresent()) {
            Champion champion = optionalChampion.get();
            champion.setChamp_rate(avg);
            championRepository.save(champion);
            return true;
        }
        return false;
    }

    // 🔹 챔피언 댓글 페이징 조회
    public Page<Champion_comment> getCommentsByChampion(int champCode, Pageable pageable) {
        return championCommentRepository.findByChampCode(champCode, pageable);
    }

    // 🔹 챔피언 댓글 작성
    public boolean commentWrite(Champion_comment comment) {
        try {
            championCommentRepository.save(comment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 🔹 챔피언 답글 작성
    public boolean recomment(Champion_comment comment) {
        try {
            championCommentRepository.save(comment);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 🔹 챔피언 코드별 댓글 개수 조회
    public long getCount(int champCode) {
        return championCommentRepository.countByChampCode(champCode);
    }
    
    // 챔피언 목록 조회 (페이징 적용)
    public Page<Champion> champList(Pageable pageable) {
        return championRepository.findAll(pageable);
    }

    // 챔피언 목록을 List<Champion>으로 변환하여 반환
    public List<Champion> getChampListAsList(Pageable pageable) {
        return championRepository.findAll(pageable).getContent();
    }
}
