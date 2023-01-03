package com.test.secciii.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer employerId;
    private String projectName;
    private String introduction;
    private Integer subtasksNum;
    private Integer finished;

    public Project(){};
}
