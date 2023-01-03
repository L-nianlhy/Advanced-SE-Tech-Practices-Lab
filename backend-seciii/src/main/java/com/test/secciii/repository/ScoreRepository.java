package com.test.secciii.repository;

import com.test.secciii.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score,Integer> {
    List<Score> findByEmployeeIdAndReportId(Integer EmployeeId, Integer ReportId);
    List<Score> findByReportId(Integer ReportId);
    List<Score> findByEmployeeId(Integer EmployeeId);
}
