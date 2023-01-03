package com.test.secciii.repository;

import com.test.secciii.entity.Evaluation;
import com.test.secciii.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation,Integer> {
    List<Evaluation> findByReportId(Integer ReportId);
    List<Evaluation> findByEmployeeId(Integer EmployeeId);
    List<Evaluation> findByEmployeeIdAndReportId(Integer EmployeeId,Integer ReportId);
}