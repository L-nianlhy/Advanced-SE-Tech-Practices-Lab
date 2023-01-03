package com.test.secciii.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileRepositoryTest {
    @Autowired
    private FileRepository fileRepository;

    @Test
    void findAllTest(){
        System.out.println(fileRepository.findAll());
    }

}