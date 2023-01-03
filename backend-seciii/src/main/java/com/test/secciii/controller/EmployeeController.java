package com.test.secciii.controller;

import com.test.secciii.entity.Employee;
import com.test.secciii.entity.Rankl;
import com.test.secciii.entity.Result;
import com.test.secciii.service.EmployeeService;
import com.test.secciii.vo.EmployeeVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;
    @PostMapping("/getInfoByEmployeeId")
    public EmployeeVo getInfoByEmployeeId(@RequestParam Integer employeeId){return employeeService.getInfoByEmployeeId(employeeId);}
    @PostMapping("/setEmployeeInfo")
    public Result<Employee> setEmployeeInfo(@RequestBody EmployeeVo employeeVo){
        return employeeService.setEmployeeInfo(
                employeeVo.getEmployeeId() ,
                String.join(",",employeeVo.getProfessionalAbility()) ,
                employeeVo.getTaskPreference() ,
                String.join(",",employeeVo.getTestDevice())
        );
    }
    @PostMapping("/getRankByEmployeeId")
    public Integer getRankByEmployeeId(@RequestParam Integer employeeId){
        return  employeeService.getRankByEmployeeId(employeeId);
    }
}
