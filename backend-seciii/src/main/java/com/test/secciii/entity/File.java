package com.test.secciii.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer taskId;
    private String fileName;
    private String fileType;
    private String filePath;

    public File(){}

    public File(Integer task_id,String file_name,String file_type,String file_path){
        this.taskId=task_id;
        this.fileName=file_name;
        this.fileType=file_type;
        this.filePath=file_path;
    }

}
