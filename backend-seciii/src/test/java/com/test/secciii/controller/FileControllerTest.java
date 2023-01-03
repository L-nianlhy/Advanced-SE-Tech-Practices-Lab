package com.test.secciii.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileControllerTest {
    @Autowired
    private FileController fileController;
    @Test
    void upload() throws Exception {
      /*  String sourcePath = "D:\\seciiitest\\file1.txt";
        File file =new File(sourcePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(),fileInputStream);
        System.out.println(multipartFile.getOriginalFilename());*/
    }
}