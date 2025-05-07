package com.example.outven.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.outven.entity.Championrate;

public interface ChampionRateRepository extends JpaRepository<Championrate, Integer> {

    // 특정 챔피언의 평점 총합
    @Query(value = "SELECT SUM(rate) FROM championRate WHERE champ_code = :champCode", nativeQuery = true)
    int sumRateByChampCode(@Param("champCode") int champCode);

    // 특정 챔피언의 평점 개수
    @Query(value = "SELECT COUNT(*) FROM championRate WHERE champ_code = :champCode", nativeQuery = true)
    int countRateByChampCode(@Param("champCode") int champCode);

    // 챔피언에 대한 특정 사용자의 평점 여부 확인
    boolean existsByChampCodeAndMemberId(int champCode, String memberId);

    // 챔피언의 평균 평점 계산
    @Query("SELECT AVG(c.rate) FROM Championrate c WHERE c.champCode = :champCode")
    Optional<Double> getAverageRate(@Param("champCode") int champCode);
}
