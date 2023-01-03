package com.test.secciii.controller;

import com.test.secciii.vo.SupplementVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SupplementControllerTest {
    @Autowired
    private SupplementController supplementController;
    @Test
    void completeSupplementInfoTest(){
        SupplementVo supplementVo = new SupplementVo(4,1,"补充2","缺陷描述2","复现步骤2","Android设备");
        System.out.println(supplementController.completeSupplementInfo(supplementVo));
    }
    @Test
    void getSupplementByReportIdTest(){
        System.out.println(supplementController.getSupplementByReportId(1));
    }
}
