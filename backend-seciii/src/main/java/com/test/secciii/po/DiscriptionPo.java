package com.test.secciii.po;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

@Data
public class DiscriptionPo {
    Integer discriptionId;
    Integer employeeId;
    String employeeName;
    String role;
    Integer reportId;
    String discription;
    String time;

    public DiscriptionPo(){}

    public DiscriptionPo(Integer employeeId,
            String employeeName,
            String role,
            Integer reportId,
            String discription,
            String time){
        this.employeeId=employeeId;
        this.employeeName=employeeName;
        this.role=role;
        this.reportId=reportId;
        this.discription=discription;
        this.time=time;

    }

    public DiscriptionPo(Integer employeeId,
                         Integer reportId,
                         String discription){
        this.employeeId=employeeId;
        this.reportId=reportId;
        this.discription=discription;

    }
}
