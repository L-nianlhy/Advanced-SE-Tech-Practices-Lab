package com.test.secciii.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@Entity
@Data
public class Admin {
    @Id
    private Integer adminId;
    private String rankRule;

    public Admin(){}
    public Admin(Integer adminId,String rankRule){
        this.adminId=adminId;
        this.rankRule=rankRule;
    }
}
