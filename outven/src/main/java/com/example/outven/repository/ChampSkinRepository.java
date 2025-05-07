package com.example.outven.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.outven.entity.Champ_skin;

public interface ChampSkinRepository extends JpaRepository<Champ_skin, Integer> {

    // ✅ 특정 챔피언의 모든 스킨 조회
	List<Champ_skin> findByChampCode(int champCode);

    // ✅ 특정 챔피언의 특정 스킨 조회
	Champ_skin findByChampCodeAndSkinCode(int champCode, int skinCode);
}
