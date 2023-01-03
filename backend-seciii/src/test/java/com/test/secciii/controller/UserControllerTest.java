package com.test.secciii.controller;

import com.test.secciii.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {
    @Autowired
    private UserController userController;

    @Test
    void registerTest(){
        User user1 = new User("lqb","123456","employee");
        User user2 = new User("yd","123456","employee");
        System.out.println(userController.register(user1));
        System.out.println(userController.register(user2));
    }

    @Test
    void loginTest(){
        User user1 = new User("xyf","123456","worker");
        User user2 = new User("lq","123456","worker");
        User user3 = new User("yangding","1234","worker");
        System.out.println(userController.login(user1));
        System.out.println(userController.login(user2));
        System.out.println(userController.login(user3));
    }

    @Test
    void findAllTest(){
        System.out.println(userController.findAll());
    }

    @Test
    void signUpTest(){
        System.out.println(userController.signUp(1,3));
    }

}