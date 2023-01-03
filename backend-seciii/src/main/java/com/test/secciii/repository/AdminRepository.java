package com.test.secciii.repository;

import com.test.secciii.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
    //Admin findByAdminId(Integer AdminId);
}
