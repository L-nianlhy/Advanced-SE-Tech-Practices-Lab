package com.test.secciii.controller;

import com.test.secciii.entity.Evaluation;
import com.test.secciii.entity.Report;
import com.test.secciii.entity.User;
import com.test.secciii.vo.ReportVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportControllerTest {
    @Autowired
    private ReportController reportController;
    @Test
    void newReportTest(){
        System.out.println(reportController.newReport(3,1));
    }
    @Test
    void completeReportInfoTest(){
        ReportVo report = new ReportVo(1,"report1","defect description 1","recurrence steps1","Android");
        System.out.println(reportController.completeReport(report));
    }
    @Test
    void getByTaskIdTest(){
        System.out.println(reportController.findReportsByTaskId(1));
    }
    @Test
    void getAvgScoreByReportId(){
        User user4 = new User("user4","123456","employee");
        User user5 = new User("user5","123456","employee");
        User user6 = new User("user6","123456","employee");
        User user7 = new User("user7","123456","employee");
        Evaluation evalua1 = new Evaluation(3,1,5,"1");
        Evaluation evalua2 = new Evaluation(4,1,4,"1");
        Evaluation evalua3 = new Evaluation(5,1,4,"1");
        Evaluation evalua4 = new Evaluation(6,1,3,"1");
        Evaluation evalua5 = new Evaluation(7,1,3,"1");
        System.out.println(reportController.getAvgScoreByReportId(1));
    }
    @Test
    void getSimilarTest(){
        System.out.println(reportController.getSimilarByReportId(1));
    }
    @Test
    void getSimilarityTest(){
        System.out.println(reportController.getSimilarityByReportId(1));
    }
}