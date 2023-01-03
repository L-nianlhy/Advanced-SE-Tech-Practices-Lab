package com.test.secciii.controller;

import com.test.secciii.entity.*;
import com.test.secciii.vo.ReportVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;
    @Autowired
    private ReportController reportController;
    @Autowired
    private TaskController taskController;
    @Autowired
    private UserController userController;
    @Test
    void getInfoByEmployeeIdTest() throws ParseException {
      /*  SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime= format.parse("2022-5-27 00:00:00");
        Date endTime= format.parse("2022-5-28 00:01:00");
        Task task1=new Task(2,"1","1",1,"1","4",startTime,endTime);
        Task task2=new Task(2,"1","1",1,"1","4",startTime,endTime);
        System.out.println(taskController.newTask(task1));
        System.out.println(taskController.newTask(task2));
        System.out.println(userController.signUp(12,4));
        reportController.newReport(4,12);
        ReportVo reportVo = new ReportVo(1,"1","1","1","1");
        reportController.completeReport(reportVo);
        System.out.println(employeeController.getInfoByEmployeeId(4));
      */
    }

}
