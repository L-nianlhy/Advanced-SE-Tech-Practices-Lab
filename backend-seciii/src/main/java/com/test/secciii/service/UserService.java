package com.test.secciii.service;

import com.test.secciii.entity.Application;
import com.test.secciii.entity.Rankl;
import com.test.secciii.entity.Result;
import com.test.secciii.entity.User;

import java.util.List;

public interface UserService {
    //用户注册处理
    Result<User> register(User user);
    //用户登录处理
    Result<User> login(User user);
    //封禁用户
    Result<User> forbidden(Integer userId);
    //解封用户
    Result<User> allowable(Integer userId);
    //返回所有用户数据
    List<User> findAll();
    //工人报名任务
    Result<Application> signUp(Integer employeeId,Integer taskId);
    //根据用户Id获取用户信息
    User getInfoByUserId(Integer UserId);
    //更新用户签名
    User updateUserSignature(Integer userId,String userSignature);
    //获取排名列表
    List<Rankl> getRank();
    //刷新排名列表
    List<Rankl> refreshRank();
    //判断是否有马上结束但还未提交报告的任务
    Boolean hasSoonFinishTaskOrNot(Integer userId);
}
