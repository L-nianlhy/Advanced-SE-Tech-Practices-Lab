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
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer employeeId;
    private Integer taskId;
    private String reportName;
    private String defectDescription;
    private String recurrenceSteps;
    private String deviceType;
    private String screenshotsPath;
    private Integer finish;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date submitTime;
    private String avgScore;

    public Report(){}

    public Report(Integer employeeId,Integer taskId,Integer finishOrNot){
        this.employeeId=employeeId;
        this.taskId=taskId;
        this.finish=finishOrNot;
    }

    public Report(String reportName,String defectDescription,String recurrenceSteps,String deviceType){
        this.reportName=reportName;
        this.defectDescription=defectDescription;
        this.recurrenceSteps=recurrenceSteps;
        this.deviceType=deviceType;
    }
}
