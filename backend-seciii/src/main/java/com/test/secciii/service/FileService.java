package com.test.secciii.service;

import com.test.secciii.entity.File;
import com.test.secciii.entity.Result;
import com.test.secciii.entity.Screenshots;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface FileService {
    //文件上传
    Result<File> upload(MultipartFile multipartFile,Integer taskId);

    //文件下载
    void download(Integer taskId, HttpServletResponse response);

    //报告截图上传
    Result<Screenshots> uploadScreenshots(MultipartFile multipartFile, String reportId);

    //报告截图地址获取
    Result<List<String>> getScreenshots(Integer reportId);

    //获取文件流
    void getRealImages(HttpServletRequest req, HttpServletResponse resp, String address) throws IOException;

    //文件下载
    void downloadScreenshots(Integer reportId, HttpServletResponse response);
}
