package com.test.secciii.repository;

import com.test.secciii.entity.Rankl;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RanklRepository extends JpaRepository<Rankl,Integer> {
    List<Rankl> findByEmployeeId(Integer employeeId);
}
