package com.test.secciii.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer employeeId;
    private Integer reportId;
    private Integer score;
    private String discription;

    public Evaluation(){}

    public Evaluation(Integer employeeId,Integer reportId,Integer score, String description){
        this.employeeId = employeeId;
        this.reportId = reportId;
        this.score = score;
        this.discription = description;
    }

    public Evaluation(Integer score,String discription){
        this.score = score;
        this.discription = discription;
    }
}
