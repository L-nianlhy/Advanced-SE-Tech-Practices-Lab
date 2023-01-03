package com.test.secciii.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String userRole;
    private String userSignature;
    private Integer loginFlag;

    public User(String username,String password,String user_role){
        this.username=username;
        this.password=password;
        this.userRole=user_role;
        this.userSignature = null;
        this.loginFlag = 1;
    }

    public User(){};
}
