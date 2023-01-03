package com.test.secciii.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
public class ReportVo {
    Integer rId;
    String reportName;
    String defectDescription;
    String recurrenceSteps;
    String deviceType;

    public ReportVo(Integer rId,String reportName,String defectDescription,String recurrenceSteps,String deviceType){
        this.rId=rId;
        this.reportName=reportName;
        this.defectDescription=defectDescription;
        this.recurrenceSteps=recurrenceSteps;
        this.deviceType=deviceType;
    }
}
