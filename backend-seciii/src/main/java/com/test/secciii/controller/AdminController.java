package com.test.secciii.controller;

import com.test.secciii.entity.Admin;
import com.test.secciii.entity.Rankl;
import com.test.secciii.entity.Result;
import com.test.secciii.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;
    @PostMapping("/getInfoByAdminId")
    public Admin getInfoByAdminId(@RequestParam Integer adminId){
        return adminService.getInfoByAdminId(adminId);
    }
    @PostMapping("/setRecommendInfo")
    public Result<String> setRecommendInfo(@RequestParam String rankRule){
        return adminService.setRecommendInfo(rankRule);
    }
    @GetMapping("/getAllTaskDevice")
    public List<Double> getAllTaskDevice(){return adminService.getAllTaskDevice();}
    @GetMapping("/getAllTaskType")
    public List<Double> getAllTaskType(){return adminService.getAllTaskType();}
    @GetMapping("/getAllEmployeeTaskPreference")
    public List<Double> getAllEmployeeTaskPreference(){return adminService.getAllEmployeeTaskPreference();}
    @GetMapping("/getAllUserActivityLevel")
    public List<Double> getAllUserActivityLevel(){return adminService.getAllUserActivityLevel();}
}
