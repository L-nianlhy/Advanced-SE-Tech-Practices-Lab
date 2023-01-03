package com.test.secciii.repository;

import com.test.secciii.entity.Discription;
import com.test.secciii.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscriptionRepository extends JpaRepository<Discription,Integer> {
    List<Discription> findByReportId(Integer reportId);
    List<Discription> findByEmployeeId(Integer employeeId);
}
