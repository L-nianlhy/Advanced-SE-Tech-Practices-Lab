package com.test.secciii.controller;

import ai.djl.ModelException;
import ai.djl.translate.TranslateException;
import com.test.secciii.entity.Report;
import com.test.secciii.entity.Result;
import com.test.secciii.entity.User;
import com.test.secciii.service.ReportService;
import com.test.secciii.vo.ReportVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Resource
    private ReportService reportService;
    @PostMapping("/newReport")
    Result<Report> newReport(@RequestParam Integer employeeId,@RequestParam Integer taskId){
        return reportService.newReport(employeeId,taskId);
    }
    @PostMapping("/completeReport")
    Result<Report> completeReport(@RequestBody ReportVo reportvo){
        return reportService.completeReportInfo(reportvo);
    }
    @PostMapping("/findReportsByTaskIdAndEmployeeId")
    Result<Report> findReportsByTaskIdAndEmployeeId(@RequestParam Integer taskId,@RequestParam Integer employeeId){
        return reportService.getReportsByTaskIdAndEmployeeId(taskId,employeeId);
    }
    @PostMapping("/findReportsByTaskId")
    List<Report> findReportsByTaskId(@RequestParam Integer taskId){
        return reportService.getReportsByTaskId(taskId);
    }
    @PostMapping("/getAvgScoreByReportId")
    Double getAvgScoreByReportId(@RequestParam Integer reportId){return reportService.getAvgScoreByReportId(reportId);}
    @PostMapping("/getSimilarByReportId")
    List<Report> getSimilarByReportId(@RequestParam Integer reportId){return reportService.getSimilarByReportId(reportId);}
    @PostMapping("/getSimilarityByReportId")
    List<Double> getSimilarityByReportId(@RequestParam Integer reportId){
        return reportService.getSimilarityByReportId(reportId);
    }
    @PostMapping("/getEmployeesByTaskId")
    public List<User> getEmployeesByTaskId(@RequestParam Integer taskId){ return reportService.getEmployeesByTaskId(taskId);}

    @PostMapping("/getImageAndTextSimilarity")
    public float getImageAndTextSimilarity(@RequestParam Integer reportId) throws ModelException, TranslateException, IOException {
        System.out.println(reportId);
        return reportService.getImageAndTextSimilarity(reportId);}
}
