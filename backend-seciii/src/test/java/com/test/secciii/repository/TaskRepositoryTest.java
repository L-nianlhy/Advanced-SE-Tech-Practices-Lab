package com.test.secciii.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;
    @Test
    void findAll(){
        System.out.println(taskRepository.findAll());
    }
}