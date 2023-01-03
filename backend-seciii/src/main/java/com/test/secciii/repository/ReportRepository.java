package com.test.secciii.repository;

import com.test.secciii.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Integer> {
    List<Report> findByTaskIdAndFinish(Integer taskId,Integer finish);
    List<Report> findByEmployeeIdAndFinish(Integer employeeId,Integer finish);
    List<Report> findByTaskId(Integer taskId);
    List<Report> findByTaskIdAndEmployeeIdAndFinish(Integer taskId,Integer employeeId,Integer Finish);
}
