package com.test.secciii.repository;

import com.test.secciii.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    //Employee findByEmployeeId(Integer EmployeeId);
}
