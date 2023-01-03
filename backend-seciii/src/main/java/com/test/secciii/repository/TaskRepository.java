package com.test.secciii.repository;

import com.test.secciii.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task> findByEmployerId(Integer employerId);
    List<Task> findByEmployerIdAndAvailable(Integer available,Integer Available);
    List<Task> findByEmployerIdAndTaskType(Integer employerId,String taskType);
    List<Task> findByProjectId(Integer projectId);
}
