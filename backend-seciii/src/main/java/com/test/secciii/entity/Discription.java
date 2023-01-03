package com.test.secciii.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Discription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer employeeId;
    private Integer reportId;
    private String discription;
    private String time;
    public Discription(){}

    public Discription(Integer employeeId,Integer reportId,String description){
        this.employeeId = employeeId;
        this.reportId = reportId;
        this.discription = description;
    }

    public Discription(String discription){
        this.discription = discription;
    }
}
