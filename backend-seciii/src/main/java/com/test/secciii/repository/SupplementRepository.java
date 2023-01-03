package com.test.secciii.repository;

import com.test.secciii.entity.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplementRepository extends JpaRepository<Supplement,Integer> {
    List<Supplement> findByReportId(Integer reportId);
    List<Supplement> findByEmployeeIdAndReportId(Integer employeeId,Integer ReportId);
    List<Supplement> findByEmployeeId(Integer employeeId);
}
