package com.test.secciii.vo;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeVo {
    Integer employeeId;
    List<String> professionalAbility;
    String taskPreference;
    List<String> testDevice;
    Integer activityLevel;
    Double avgScore;
    Double finishRate;
    Integer suppNum;
    Double reviewAbility;
    Double reportSimilarity;
    Double totalScore;

    public EmployeeVo(
            Integer employeeId,
            List<String> professionalAbility,
            String taskPreference,
            List<String> testDevice){
        this.employeeId=employeeId;
        this.professionalAbility=professionalAbility;
        this.taskPreference=taskPreference;
        this.testDevice=testDevice;
    }

    public EmployeeVo(){}

    public EmployeeVo(
            Integer employeeId,
            List<String> professionalAbility,
            String taskPreference,
            List<String> testDevice,
            Integer activityLevel,
            Double avgScore,
            Double finishRate,
            Integer suppNum,
            Double reviewAbility,
            Double reportSimilarity,
            Double totalScore){
        this.employeeId=employeeId;
        this.professionalAbility=professionalAbility;
        this.taskPreference=taskPreference;
        this.testDevice=testDevice;
        this.activityLevel=activityLevel;
        this.avgScore=avgScore;
        this.finishRate=finishRate;
        this.suppNum=suppNum;
        this.reviewAbility=reviewAbility;
        this.reportSimilarity=reportSimilarity;
        this.totalScore=totalScore;
    }
}
