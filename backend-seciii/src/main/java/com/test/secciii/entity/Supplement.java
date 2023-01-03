package com.test.secciii.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Supplement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer employeeId;
    private Integer reportId;
    private String supplementName;
    private String defectDescription;
    private String recurrenceSteps;
    private String deviceType;
    private String screenshotsPath;

    public Supplement(){}

    public Supplement(Integer employeeId,Integer reportId){
        this.employeeId=employeeId;
        this.reportId=reportId;

    }

    public Supplement(Integer employeeId,Integer reportId,String supplementName,String defectDescription,String recurrenceSteps,String deviceType){
        this.employeeId=employeeId;
        this.reportId=reportId;
        this.supplementName=supplementName;
        this.defectDescription=defectDescription;
        this.recurrenceSteps=recurrenceSteps;
        this.deviceType=deviceType;
    }

}
