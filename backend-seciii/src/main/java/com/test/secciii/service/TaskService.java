package com.test.secciii.service;

import com.test.secciii.entity.Result;
import com.test.secciii.entity.Task;

import java.util.List;

public interface TaskService {
    //处理新建测试任务
    Result<Task> newTask(Task task);
    //根据用户id获取所有任务
    List<Task> getAllTasks(Integer userId);
    //获取某一发包方的任务
    List<Task> getTasksByUserId(Integer uid);
    //获取某工人参加过且未提交报告的任务
    List<Task> getNotSubmitTasks(Integer employeeId);
    //获取某工人参加过且提交过报告的任务
    List<Task> getSubmitTasks(Integer employeeId);
    //获取某任务已报名人数和总人数[Signed,Total]
    List<Integer> getSignedAndTotalNum(Integer taskId);
    //获取某任务的剩余时间
    Boolean getAvailableOrNotByTaskId(Integer taskId);
    //获取所有功能测试
    List<Task> getFTaskByEmployerId(Integer employerId);
    //获取所有性能测试
    List<Task> getPTaskByEmployerId(Integer employerId);
    //获取已结束测试
    List<Task> getEndTaskByEmployerId(Integer employerId);
    //获取进行中任务
    List<Task> getIntestTaskByEmployerId(Integer employerId);
    //按照时间顺序获取任务
    List<Task> getTaskByEmployeeIdInOrder(Integer employeeId);
}
