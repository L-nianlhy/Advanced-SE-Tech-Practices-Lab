package com.test.secciii.serviceImpl;

import com.test.secciii.entity.Project;
import com.test.secciii.entity.Result;
import com.test.secciii.entity.Task;
import com.test.secciii.repository.ProjectRepository;
import com.test.secciii.repository.TaskRepository;
import com.test.secciii.service.ProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private ProjectRepository projectRepository;
    @Resource
    private TaskRepository taskRepository;

    @Override
    public Result<Project> newProject(Project project) {
        project.setSubtasksNum(0);
        project.setFinished(1);
        return new Result<>(1,"项目创建成功！",projectRepository.save(project));
    }

    @Override
    public Result<Project> getProjectByID(Integer id) {
        Project target = projectRepository.findById(id).get();
        return new Result<>(1,"项目详情获取成功！",target);
    }

    @Override
    public List<Project> getAllProjects(Integer userId) {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> getProjectByEmployerID(Integer employerID) {
        return projectRepository.findByEmployerId(employerID);
    }

    @Override
    public List<Task> getTasksByProjectID(Integer projectId) {
        return taskRepository.findByProjectId(projectId);
    }
}
