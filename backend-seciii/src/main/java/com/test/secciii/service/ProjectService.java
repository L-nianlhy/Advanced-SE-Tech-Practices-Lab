package com.test.secciii.service;

import com.test.secciii.entity.Project;
import com.test.secciii.entity.Result;
import com.test.secciii.entity.Task;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ProjectService {
    //处理新建测试项目
    Result<Project> newProject(Project project);
    //获取项目详情
    Result<Project> getProjectByID(Integer id);
    //根据用户id获取所有项目
    List<Project> getAllProjects(Integer userId);
    //获取某一发包方的项目
    List<Project> getProjectByEmployerID(Integer employerID);
    //获取项目下任务列表
    List<Task> getTasksByProjectID(Integer projectId);
}
