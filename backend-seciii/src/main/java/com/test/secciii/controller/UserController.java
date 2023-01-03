package com.test.secciii.controller;

import com.test.secciii.entity.Application;
import com.test.secciii.entity.Rankl;
import com.test.secciii.entity.Result;
import com.test.secciii.entity.User;
import com.test.secciii.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user){
        return userService.register(user);
    }
    @PostMapping("/login")
    public Result<User> login(@RequestBody User user){
        System.out.println(user.getUsername());
        return userService.login(user);
    }
    @PostMapping("/forbidden")
    public Result<User> forbidden(@RequestParam Integer userId){
        return userService.forbidden(userId);
    }
    @PostMapping("/allowable")
    public Result<User> allowable(@RequestParam Integer userId){
        return userService.allowable(userId);
    }
    @GetMapping("/findAll")
    public List<User> findAll(){
        return userService.findAll();
    }
    @PostMapping("/signUp")
    public Result<Application> signUp(@RequestParam Integer taskId,@RequestParam Integer employeeId){
        return userService.signUp(employeeId,taskId);
    }
    @PostMapping("/getInfoByUserId")
    public User getInfoByUserId(@RequestParam Integer userId){
        return userService.getInfoByUserId(userId);
    }
    @PostMapping("/updateUserSignature")
    public User updateUserSignature(@RequestParam Integer userId,@RequestParam String userSignature){
        return userService.updateUserSignature(userId,userSignature);
    }
    @PostMapping("/getRank")
    public List<Rankl> getRank(){return userService.getRank();}
    @PostMapping("/refreshRank")
    public List<Rankl> refreshRank(){return userService.refreshRank();}

    @PostMapping("/hasSoonFinishTaskOrNot")
    public Boolean hasSoonFinishTaskOrNot(@RequestParam Integer userId){return userService.hasSoonFinishTaskOrNot(userId);}
}
