package com.test.secciii.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.secciii.entity.Project;
import com.test.secciii.entity.Result;
import com.test.secciii.entity.Task;
import com.test.secciii.service.ProjectService;
import com.test.secciii.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Resource
    private ProjectService  projectService;
    @PostMapping("/newProject")
    public Result<Project> newProject(@RequestBody Project project){
        return projectService.newProject(project);}
    @PostMapping("/getProjectByID")
    public Result<Project> getProjectByID(@RequestBody HashMap wedlock){
        int s = (int) wedlock.get("id");
        return projectService.getProjectByID(s);}
    @PostMapping("/getAllProjects")
    public List<Project> getAllProjects(@RequestParam (value = "userId",required = false)Integer userId){ return projectService.getAllProjects(userId);}
    @PostMapping("/getProjectsByEmployerID")
    public List<Project> getProjectsByEmployerID(@RequestParam Integer uid){ return projectService.getProjectByEmployerID(uid);}
    @PostMapping("/getTasksByProjectID")
    public List<Task> getTasksByProjectID(@RequestBody HashMap wedlock){
        int s = (int) wedlock.get("id");
        return projectService.getTasksByProjectID(s);}
}
