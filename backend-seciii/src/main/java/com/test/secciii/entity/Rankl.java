package com.test.secciii.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
@Data
public class Rankl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer employeeId;
    private String employeeName;
    private String score;

    public Rankl(){}
    public Rankl(Integer employeeId,String score){
        this.employeeId=employeeId;
        this.score=score;
    }

    public Rankl(Integer id,Integer employeeId,String employeeName,String score){
        this.id=id;
        this.employeeName=employeeName;
        this.employeeId=employeeId;
        this.score=score;
    }


}
