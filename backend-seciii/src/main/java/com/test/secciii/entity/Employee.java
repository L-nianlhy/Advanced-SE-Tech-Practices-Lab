package com.test.secciii.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Employee {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;
    private String professionalAbility;
    private String taskPreference;
    private String testDevice;
    private Integer activityLevel;
    private String avgScore;
    private String finishRate;
    private Integer suppNum;
    private String reviewAbility;
    private String reportSimilarity;
    private String totalScore;

    public Employee(){}

    public Employee(Integer employeeId){
        this.employeeId=employeeId;
    }

    public Employee(Integer employeeId,String professionalAbility,String taskPreference,String testDevice){
     this.employeeId=employeeId;
     this.professionalAbility=professionalAbility;
     this.taskPreference=taskPreference;
     this.testDevice=testDevice;
    }
}
