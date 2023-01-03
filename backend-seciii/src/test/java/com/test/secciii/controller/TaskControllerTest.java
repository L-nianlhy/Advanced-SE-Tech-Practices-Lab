package com.test.secciii.controller;

import com.test.secciii.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskControllerTest {
    @Autowired
    private TaskController taskController;

    @Test
    void newTaskTest(){
        Task task1 = new Task(1,"task2","task2_intro",5,"1","Android设备");
        Task task2 = new Task(2,"task3","task3_intro",5,"0","鸿蒙设备");
        Task task3 = new Task(1,"task4","task4_intro",5,"2","Mac设备");
        Task task4 = new Task(2,"task5","task5_intro",5,"3","Windows设备");
        System.out.println(taskController.newTask(task1));
        System.out.println(taskController.newTask(task2));
        System.out.println(taskController.newTask(task3));
        System.out.println(taskController.newTask(task4));
    }

    @Test
    void getAllTasksTest(){
        System.out.println(taskController.getAllTasks(1));
    }

    @Test
    void getTasksByUserId(){
        System.out.println(taskController.getTasksByUserId(1));
    }

    @Test
    void getNotSubmitTasks(){
        System.out.println(taskController.getNotSubmitTasks(3));
    }

    @Test
    void getSubmitTasks(){
        System.out.println(taskController.getSubmitTasks(3));
    }
}