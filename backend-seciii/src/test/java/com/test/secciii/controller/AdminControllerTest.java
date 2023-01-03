package com.test.secciii.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminControllerTest {
    @Autowired
    private AdminController adminController;

    @Test
    void getInfoByAdminIdTest(){
        System.out.println(adminController.getInfoByAdminId(1));
    }
    @Test
    void setRecommendInfoTest(){
        System.out.println(adminController.setRecommendInfo("TestDevice"));
    }



}
