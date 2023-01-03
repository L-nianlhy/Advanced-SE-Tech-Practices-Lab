package com.test.secciii.service;

import com.test.secciii.entity.Admin;
import com.test.secciii.entity.Result;

import java.util.List;

public interface AdminService {
    //根据管理员id返回管理员信息
    Admin getInfoByAdminId(Integer adminId);
    //设置推荐信息
    Result<String> setRecommendInfo(String rankRule);
    //获取众测任务测试设备分布
    List<Double> getAllTaskDevice();
    //获取众测任务测试类型分布
    List<Double> getAllTaskType();
    //获取众测工人任务偏好分布
    List<Double> getAllEmployeeTaskPreference();
    //获取众测工人活跃度分布
    List<Double> getAllUserActivityLevel();
}
