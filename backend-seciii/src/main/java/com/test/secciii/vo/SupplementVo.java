package com.test.secciii.vo;

import lombok.Data;

@Data
public class SupplementVo {
    Integer employeeId;
    Integer reportId;
    String supplementName;
    String defectDescription;
    String recurrenceSteps;
    String deviceType;

    public SupplementVo(Integer employeeId, Integer reportId, String supplementName,String defectDescription,String recurrenceSteps,String deviceType) {
        this.employeeId=employeeId;
        this.reportId=reportId;
        this.supplementName = supplementName;
        this.defectDescription = defectDescription;
        this.recurrenceSteps = recurrenceSteps;
        this.deviceType = deviceType;
    }
}
