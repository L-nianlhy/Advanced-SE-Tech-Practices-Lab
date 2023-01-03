package com.test.secciii.controller;

import com.test.secciii.entity.Evaluation;
import com.test.secciii.entity.Task;
import com.test.secciii.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EvaluationControllerTest {
    @Autowired
    private EvaluationController evaluationController;
    private UserController userController;
    @Test
    void newEvaluationTest(){
        User user4 = new User("user4","123456","employee");
        User user5 = new User("user5","123456","employee");
        User user6 = new User("user6","123456","employee");
        User user7 = new User("user7","123456","employee");
        Evaluation evalua1 = new Evaluation(3,1,5,"1");
        Evaluation evalua2 = new Evaluation(4,1,4,"1");
        Evaluation evalua3 = new Evaluation(3,1,4,"1");
        Evaluation evalua4 = new Evaluation(6,1,3,"1");
        Evaluation evalua5 = new Evaluation(7,1,3,"1");

        System.out.println(evaluationController.newEvaluation(evalua1));
        System.out.println(evaluationController.newEvaluation(evalua2));
        System.out.println(evaluationController.newEvaluation(evalua3));
        System.out.println(evaluationController.newEvaluation(evalua4));
        System.out.println(evaluationController.newEvaluation(evalua5));
    }

    @Test
    void getEvaluationByReportIdTest(){
        System.out.println(evaluationController.getEvaluationByReportId(1));
    }

}