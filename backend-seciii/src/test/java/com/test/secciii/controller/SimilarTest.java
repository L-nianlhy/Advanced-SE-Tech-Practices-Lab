package com.test.secciii.controller;

import com.test.secciii.serviceImpl.Similarity;
import org.junit.jupiter.api.Test;

public class SimilarTest {
    @Test
    void SimilarityTest(){
        System.out.println(Similarity.getSimilarity("TaskController","UserController"));
        System.out.println(Similarity.getSimilarity("罗庆滨","罗庆滨一号"));
        System.out.println(Similarity.getSimilarity("He has cats in his bag","He has a cat in his bag"));
        System.out.println(Similarity.getSimilarity("934","1awdkn2l4l3ackjcbwba059"));
    }
}
