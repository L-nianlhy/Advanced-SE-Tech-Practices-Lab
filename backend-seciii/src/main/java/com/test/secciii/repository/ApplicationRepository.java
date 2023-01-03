package com.test.secciii.repository;

import com.test.secciii.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application,Integer> {
    Application findByTaskIdAndEmployeeId(Integer taskId,Integer employeeId);
    List<Application> findByEmployeeIdAndSubmit(Integer employeeId,Integer submit);
    List<Application> findByEmployeeId(Integer employeeId);
    List<Application> findByTaskIdAndSubmit(Integer taskId,Integer submit);
    List<Application> findByTaskId(Integer taskId);
}
