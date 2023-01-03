package com.test.secciii.controller;

import com.test.secciii.entity.File;
import com.test.secciii.entity.Result;
import com.test.secciii.entity.Screenshots;
import com.test.secciii.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
public class FileController {

    @Resource
    private FileService fileService;

    @RequestMapping(value = "/upload")
    public Result<File> upload(@RequestParam("file")MultipartFile multipartFile, @RequestParam Integer taskId){
        return fileService.upload(multipartFile,taskId);
    }

    @RequestMapping(value = "/download")
    public void download(@RequestParam Integer taskId, HttpServletResponse response){
        fileService.download(taskId,response);
    }

    @RequestMapping(value = "/uploadScreenshots")
    public Result<Screenshots> uploadScreenshots(@RequestParam("file")MultipartFile multipartFile, @RequestParam String reportId){
        return fileService.uploadScreenshots(multipartFile,reportId);
    }

    @GetMapping(value = "/getScreenshots")
    public Result<List<String>> getScreenshots(@RequestParam Integer reportId){
        return fileService.getScreenshots(reportId);
    }

    @RequestMapping("/getRealImages")
    //返回文件流
    public void getLogo(HttpServletRequest req, HttpServletResponse resp, @RequestParam String address) throws IOException {
        fileService.getRealImages(req,resp,address);
    }
    @RequestMapping(value = "/downloadScreenshots")
    public void downloadScreenshots(@RequestParam Integer reportId, HttpServletResponse response){
        fileService.downloadScreenshots(reportId,response);
    }
}
