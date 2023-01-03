package com.test.secciii.repository;

import com.test.secciii.entity.Screenshots;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreenshotsRepository extends JpaRepository<Screenshots,Integer> {
    List<Screenshots> findByReportId(Integer reportId);
}
