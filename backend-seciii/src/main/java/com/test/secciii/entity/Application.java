package com.test.secciii.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer taskId;
    private Integer employeeId;
    private Integer submit;

    public Application(){}

    public Application(Integer taskId,Integer employeeId,Integer submitOrNot){
        this.taskId=taskId;
        this.employeeId=employeeId;
        this.submit=submitOrNot;
    }
}
