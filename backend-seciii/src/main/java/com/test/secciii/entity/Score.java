package com.test.secciii.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer employeeId;
    private Integer reportId;
    private Integer score;


    public Score(){}

    public Score(Integer employeeId,Integer reportId,Integer score){
        this.employeeId = employeeId;
        this.reportId = reportId;
        this.score = score;

    }

    public Score(Integer score){
        this.score = score;
    }
}
