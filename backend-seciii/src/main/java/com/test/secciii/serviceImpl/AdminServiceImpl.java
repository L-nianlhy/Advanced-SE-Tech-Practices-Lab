package com.test.secciii.serviceImpl;

import com.test.secciii.entity.Admin;
import com.test.secciii.entity.Employee;
import com.test.secciii.entity.Result;
import com.test.secciii.entity.Task;
import com.test.secciii.repository.AdminRepository;
import com.test.secciii.repository.TaskRepository;
import com.test.secciii.repository.EmployeeRepository;
import com.test.secciii.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminRepository adminRepository;
    @Resource
    private TaskRepository taskRepository;
    @Resource
    private EmployeeRepository employeeRepository;

    @Override
    public Admin getInfoByAdminId(Integer adminId){
        Admin admin = adminRepository.findById(adminId).get();
        return admin;
    }

    @Override
    public Result<String> setRecommendInfo(String rankRule){
        List<Admin> adminList = adminRepository.findAll();
        for(int i=0;i<adminList.size();i++){
            adminList.get(i).setRankRule(rankRule);
            adminRepository.save(adminList.get(i));
        }
        return new Result<>(1,"设置成功！",rankRule);
    }

    @Override
    public List<Double> getAllTaskDevice(){
        List<Double> result = new ArrayList<>();
        List<Task> tasks = taskRepository.findAll();
        int total = tasks.size();
        System.out.println(tasks);
        double temp = 0.00;
        int n_Linux=0;
        int n_Android=0;
        int n_Harmony=0;
        int n_Mac=0;
        int n_Windows=0;
        for(int i=0;i<tasks.size();i++){
            if(tasks.get(i).getTestDevice().equals("Linux设备")){
                n_Linux++;
            }
            else if (tasks.get(i).getTestDevice().equals("Android设备")){
                n_Android++;
            }
            else if (tasks.get(i).getTestDevice().equals("鸿蒙设备")){
                n_Harmony++;
            }
            else if (tasks.get(i).getTestDevice().equals("Mac设备")){
                n_Mac++;
            }
            else {
                n_Windows++;
            }
        }
        temp = (double)n_Linux/total;
        result.add(temp);
        temp = (double)n_Android/total;
        result.add(temp);
        temp = (double) n_Harmony/total;
        result.add(temp);
        temp = (double) n_Mac/total;
        result.add(temp);
        temp = (double) n_Windows/total;
        result.add(temp);
        return result;
    }

    @Override
    public List<Double> getAllTaskType(){
        List<Double> result = new ArrayList<>();
        int[] n_types = new int[3];
        List<Task> tasks = taskRepository.findAll();
        int total=tasks.size();
        for(int i=0;i<tasks.size();i++){
            if(tasks.get(i).getTaskType().equals("功能测试")){
                n_types[0]++;
            }
            else if (tasks.get(i).getTaskType().equals("性能测试")){
                n_types[1]++;
            }
            else {
                n_types[2]++;
            }
        }
        for(int i=0;i<n_types.length;i++){
            result.add((double) n_types[i]/total);
        }
        return result;
    }

    @Override
    public List<Double> getAllEmployeeTaskPreference(){
        List<Double> result = new ArrayList<>();
        int[] n_types = new int[4];
        List<Employee> employees = employeeRepository.findAll();
        int total=employees.size();
        for(int i=0;i<total;i++){
            if(employees.get(i).getTaskPreference().equals("其他")){
                n_types[3]++;
            }
            else if (employees.get(i).getTaskPreference().equals("功能测试")){
                n_types[1]++;
            }
            else if (employees.get(i).getTaskPreference().equals("性能测试")){
                n_types[2]++;
            }
            else {
                n_types[0]++;
            }
        }
        for(int i=0;i<n_types.length;i++){
            result.add((double) n_types[i]/total);
        }
        return result;
    }

    @Override
    public List<Double> getAllUserActivityLevel(){
        List<Double> result = new ArrayList<>();
        int[] n_types = new int[3];
        List<Employee> employees = employeeRepository.findAll();
        int total=employees.size();
        for(int i=0;i<total;i++){
            if(employees.get(i).getActivityLevel()<30){
                n_types[0]++;
            }
            else if (employees.get(i).getActivityLevel()<70){
                n_types[1]++;
            }
            else if (employees.get(i).getActivityLevel()<=100){
                n_types[2]++;
            }
        }
        for(int i=0;i<n_types.length;i++){
            result.add((double) n_types[i]/total);
        }
        return result;
    }
}
