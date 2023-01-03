package com.test.secciii.service;

import com.test.secciii.entity.Employee;
import com.test.secciii.entity.Rankl;
import com.test.secciii.entity.Result;
import com.test.secciii.vo.EmployeeVo;

public interface EmployeeService {
    //根据工人id返回工人信息
    EmployeeVo getInfoByEmployeeId(Integer employeeId);
    //设置工人信息
    Result<Employee> setEmployeeInfo(Integer employeeId, String professionalAbility, String taskPreference, String testDevice);
    //获取工人评分的排名
    Integer getRankByEmployeeId(Integer employeeId);
}
