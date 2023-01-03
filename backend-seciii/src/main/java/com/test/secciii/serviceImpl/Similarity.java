package com.test.secciii.serviceImpl;


import cn.hutool.core.util.StrUtil;

public class Similarity {

    public static double getSimilarity(String doc1, String doc2) {
        if(doc1.equals("")||doc2.equals(""))return 0.0;
        else return StrUtil.similar(doc1, doc2);
    }


}
