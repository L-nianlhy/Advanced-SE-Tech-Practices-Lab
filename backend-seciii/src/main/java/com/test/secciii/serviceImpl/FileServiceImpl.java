package com.test.secciii.serviceImpl;

import com.test.secciii.controller.FileController;
import com.test.secciii.entity.*;
import com.test.secciii.repository.FileRepository;
import com.test.secciii.repository.ReportRepository;
import com.test.secciii.repository.ScreenshotsRepository;
import com.test.secciii.repository.TaskRepository;
import com.test.secciii.service.FileService;
import com.test.secciii.utils.ZipFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    private FileRepository fileRepository;
    @Resource
    private TaskRepository taskRepository;
    @Resource
    private ScreenshotsRepository screenshotsRepository;
    @Resource
    private ReportRepository reportRepository;


    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public Result<File> upload(MultipartFile multipartFile,Integer taskId) {
        try {
            if (multipartFile.isEmpty()){log.info("The file is null!");}
            else {log.info("file receive successfully!");}

            // 获取文件名
            String fileName =  URLEncoder.encode(multipartFile.getOriginalFilename(),"utf-8");
            log.info("filename: " + fileName);//写日志
            /*fileName = URLDecoder.decode(fileName,"UTF-8");
            log.info(fileName);*/

            // 设置文件存储路径         *************************************************
            String filePath = "seciiiFile//"+taskId.toString();
            java.io.File taskDir = new java.io.File(filePath);
            taskDir.setWritable(true);
            taskDir.setReadable(true);
            taskDir.setExecutable(true);
            if(!taskDir.exists()){taskDir.mkdirs();}

            //存储文件
            filePath = filePath+ java.io.File.separator+fileName;
            java.io.File dest = new java.io.File(taskDir.getAbsolutePath()+ "/" + fileName);
            dest.setExecutable(true);
            dest.setReadable(true);
            dest.setWritable(true);
            multipartFile.transferTo(dest);

            //将文件信息写入数据库中
            File fileUploading = new File(taskId,fileName,"testFile",filePath);
            File fileUploaded = fileRepository.save(fileUploading);


            return new Result<>(1,"文件上传成功！",fileUploaded);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result<>(0,"文件上传失败！",new File());
    }

    @Override
    public void download(Integer taskId, HttpServletResponse response) {
        //获取目标任务信息
        Task targetTask = taskRepository.findById(taskId).get();
        String targetTaskPath = targetTask.getFilePath();
        String taskName = targetTask.getTaskName();
        log.info("目标项目为："+taskName+" 路径："+targetTaskPath);

        //创建文件暂存目录
        String temporaryPath = "seciiiTempory//"+ taskName;
        java.io.File temDir = new java.io.File(temporaryPath);
        temDir.setWritable(true,false);
        if (!temDir.exists()) {
            temDir.mkdirs();
        }
        log.info("创建文件暂存目录");

        //文件转移
        try {
            ZipFileUtils.copyDir(targetTaskPath, temporaryPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(taskName+".zip", "UTF-8"));
        }catch (IOException e){
            e.printStackTrace();
        }

        //打包文件并传输
        try {
            ZipFileUtils.toZip(temDir.getPath(), response.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //删除文件
        java.io.File[] listFiles = temDir.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            listFiles[i].delete();
            log.info("正在删除第"+i+"个文件");
        }
        temDir.delete();
    }

    @Override
    public void downloadScreenshots(Integer reportId, HttpServletResponse response) {
        //获取目标任务信息
        Report targetReport = reportRepository.findById(reportId).get();
        String targetReportPath = targetReport.getScreenshotsPath();
        String reportName = targetReport.getReportName();
        log.info("目标报告为："+reportName+" 路径："+targetReportPath);

        //创建文件暂存目录
        String temporaryPath = "seciiiTempory//"+ reportName;
        java.io.File temDir = new java.io.File(temporaryPath);
        temDir.setWritable(true,false);
        if (!temDir.exists()) {
            temDir.mkdirs();
        }
        log.info("创建文件暂存目录");

        //文件转移
        try {
            ZipFileUtils.copyDir(targetReportPath, temporaryPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(reportName+".zip", "UTF-8"));
        }catch (IOException e){
            e.printStackTrace();
        }

        //打包文件并传输
        try {
            ZipFileUtils.toZip(temDir.getPath(), response.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //删除文件
        java.io.File[] listFiles = temDir.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            listFiles[i].delete();
            log.info("正在删除第"+i+"个文件");
        }
        temDir.delete();
    }

    @Override
    public Result<Screenshots> uploadScreenshots(MultipartFile multipartFile, String reportId) {
        try {
            if (multipartFile.isEmpty()){log.info("The file is null!");}
            else {log.info("receive file successfully!");}

            // 获取文件名
            String fileName = multipartFile.getOriginalFilename();
            log.info("screenshotsName: " + fileName + " id:"+reportId);//写日志

            // 设置文件存储路径         *************************************************
            String filePath = "seciiiScreenshots//"+ reportId;
            java.io.File taskDir = new java.io.File(filePath);
            taskDir.setWritable(true);
            taskDir.setExecutable(true);
            taskDir.setReadable(true);
            if(!taskDir.exists()){taskDir.mkdirs();}

            //存储文件
            filePath = filePath+ java.io.File.separator+fileName;
            java.io.File dest = new java.io.File(taskDir.getAbsolutePath()+ "/" + fileName);
            dest.setReadable(true);
            dest.setExecutable(true);
            dest.setWritable(true);
            multipartFile.transferTo(dest);

            //将文件信息写入数据库中
            Screenshots screenshotUploading = new Screenshots(Integer.parseInt(reportId),fileName,filePath);
            Screenshots screenshotUploaded = screenshotsRepository.save(screenshotUploading);


            return new Result<>(1,"截图上传成功！",screenshotUploaded);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result<>(0,"截图上传失败！",new Screenshots());
    }

    @Override
    public Result<List<String>> getScreenshots(Integer reportId) {
        List<Screenshots> screenshots = screenshotsRepository.findByReportId(reportId);
        List<String> paths = new ArrayList<>();
        for(int i=0;i<screenshots.size();i++){
            paths.add(screenshots.get(i).getScreenshotsPath());
        }
        return new Result<>(1,"成功返回截图地址！",paths);
    }

    @Override
    public void getRealImages(HttpServletRequest req, HttpServletResponse resp, String address) throws IOException{
        FileInputStream fileInputStream = new FileInputStream(address);
        int available = fileInputStream.available();
        byte[] data = new byte[available];
        fileInputStream.read(data);
        fileInputStream.close();
        resp.setContentType("image/jpg;image/png;image/jpeg;image/*");
        OutputStream outputStream = resp.getOutputStream();
        outputStream.write(data);
        outputStream.close();
    }
}
