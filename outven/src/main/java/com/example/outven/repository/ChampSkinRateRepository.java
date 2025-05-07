package com.example.outven.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.outven.entity.Champskinrate;

import java.util.Optional;

public interface ChampSkinRateRepository extends JpaRepository<Champskinrate, Integer> {

    // ✅ 특정 챔피언 스킨의 평점 총합
	@Query("SELECT SUM(c.rate) FROM Champskinrate c WHERE c.champCode = :champCode AND c.skinCode = :skinCode")
	Optional<Integer> sumRateByChampCodeAndSkinCode(@Param("champCode") int champCode, @Param("skinCode") int skinCode);

    // ✅ 특정 챔피언 스킨의 평점 개수
    long countByChampCodeAndSkinCode(int champCode, int skinCode);

    // ✅ 특정 챔피언 스킨에 대해 특정 유저가 평점을 남겼는지 확인
    Optional<Champskinrate> findByChampCodeAndSkinCodeAndMemberId(int champCode, int skinCode, String memberId);

    // ✅ 챔피언 스킨의 평균 평점 구하기
    @Query("SELECT AVG(c.rate) FROM Champskinrate c WHERE c.champCode = :champCode AND c.skinCode = :skinCode")
    Optional<Double> getAverageRate(@Param("champCode") int champCode, @Param("skinCode") int skinCode);

    // ✅ 특정 유저가 특정 스킨에 평점을 남겼는지 확인
    boolean existsByChampCodeAndSkinCodeAndMemberId(int champCode, int skinCode, String memberId);
}
