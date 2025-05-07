package com.example.outven.repository;

import com.example.outven.entity.Boardreport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardReportRepository extends JpaRepository<Boardreport, Long> {
    boolean existsByBoardNumAndReporterId(int boardNum, String reporterId);
}
