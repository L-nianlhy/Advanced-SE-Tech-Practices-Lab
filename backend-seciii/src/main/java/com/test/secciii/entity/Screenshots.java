package com.test.secciii.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Screenshots {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer reportId;
    private String screenshotsName;
    private String screenshotsPath;

    public Screenshots(){};

    public Screenshots(Integer reportId,String screenshotsName,String screenshotsPath){
        this.reportId=reportId;
        this.screenshotsName=screenshotsName;
        this.screenshotsPath=screenshotsPath;
    }
}
