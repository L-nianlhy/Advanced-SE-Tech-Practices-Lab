package com.test.secciii.repository;

import com.test.secciii.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary,Integer> {
    List<Diary> findByUid(Integer uid);
}
