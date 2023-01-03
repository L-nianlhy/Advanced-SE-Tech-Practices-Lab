package com.test.secciii.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer projectId;
    private Integer employerId;
    private String taskName;
    private String introduction;
    private Integer needWorkers;
    private String testDevice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    private String taskType;
    private String filePath;
    private Integer signedNum;
    private Integer available;

    public Task(){};

    public Task(Integer employer_id,String task_name,String introduction,Integer need_workers,String taskType,String testDevice){
        this.employerId=employer_id;
        this.taskName=task_name;
        this.introduction=introduction;
        this.needWorkers=need_workers;
        this.taskType=taskType;
        this.testDevice=testDevice;
    }

    public Task(Integer employer_id,String task_name,String introduction,Integer need_workers,String taskType,String testDevice,
        Date startTime,Date endTime){
        this.employerId=employer_id;
        this.taskName=task_name;
        this.introduction=introduction;
        this.needWorkers=need_workers;
        this.taskType=taskType;
        this.testDevice=testDevice;
        this.startTime=startTime;
        this.endTime=endTime;
    }
}
