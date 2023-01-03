package com.test.secciii.controller;

import com.test.secciii.entity.Result;
import com.test.secciii.entity.Task;
import com.test.secciii.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Resource
    private TaskService taskService;
    @PostMapping("/newTask")
    public Result<Task> newTask(@RequestBody Task task){ return taskService.newTask(task);}
    @PostMapping("/getAllTasks")
    public List<Task> getAllTasks(@RequestParam Integer userId){ return taskService.getAllTasks(userId);}
    @PostMapping("/getTasksByUserId")
    public List<Task> getTasksByUserId(@RequestParam Integer uid){ return taskService.getTasksByUserId(uid);}
    @PostMapping("/getNotSubmitTasks")
    public List<Task> getNotSubmitTasks(@RequestParam Integer employeeId){return taskService.getNotSubmitTasks(employeeId);}
    @PostMapping("/getSubmitTasks")
    public List<Task> getSubmitTasks(@RequestParam Integer employeeId){return taskService.getSubmitTasks(employeeId);}
    @PostMapping("/getSignedAndTotalNum")
    //List内有两个值，分别为已报名数和总数
    public List<Integer> getSignedAndTotalNum(@RequestParam Integer taskId){return taskService.getSignedAndTotalNum(taskId);}
    @PostMapping("/getEndOrNotByTaskId")
    public Boolean getAvailableOrNotByTaskId(@RequestParam Integer taskId){return taskService.getAvailableOrNotByTaskId(taskId);}
    @PostMapping("/getFTaskByEmployerId")
    public List<Task> getFTaskByEmployerId(@RequestParam Integer employerId){return taskService.getFTaskByEmployerId(employerId);}
    @PostMapping("/getPTaskByEmployerId")
    public List<Task> getPTaskByEmployerId(@RequestParam Integer employerId){return taskService.getPTaskByEmployerId(employerId);}
    @PostMapping("/getEndTaskByEmployerId")
    public List<Task> getEndTaskByEmployerId(@RequestParam Integer employerId){return taskService.getEndTaskByEmployerId(employerId);}
    @PostMapping("/getIntestTaskByEmployerId")
    public List<Task> getIntestTaskByEmployerId(@RequestParam Integer employerId){return taskService.getIntestTaskByEmployerId(employerId);}
    @PostMapping("/getTaskByEmployeeIdInOrder")
    public List<Task> getTaskByEmployeeIdInOrder(@RequestParam Integer employeeId){return taskService.getTaskByEmployeeIdInOrder(employeeId);}
}
