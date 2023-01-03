package com.test.secciii.service;

import ai.djl.ModelException;
import ai.djl.translate.TranslateException;
import com.test.secciii.entity.Report;
import com.test.secciii.entity.Result;
import com.test.secciii.entity.User;
import com.test.secciii.vo.ReportVo;

import java.io.IOException;
import java.util.List;

public interface ReportService {
    //新建报告
    Result<Report> newReport(Integer employeeId,Integer taskId);
    //完善报告信息
    Result<Report> completeReportInfo(ReportVo reportvo);
    //获取某一任务完成的报告
    List<Report> getReportsByTaskId(Integer taskId);
    //获取某一报告的得分均值
    Double getAvgScoreByReportId(Integer reportId);
    //获取某一报告的相似报告
    List<Report> getSimilarByReportId(Integer reportId);
    //获取这些相似报告的相似度
    List<Double> getSimilarityByReportId(Integer reportId);
    //获取获取某一任务某工人完成的报告
    Result<Report> getReportsByTaskIdAndEmployeeId(Integer taskId, Integer employeeId);
    //获取某任务下的工人列表
    List<User> getEmployeesByTaskId(Integer taskId);
    //获取某个报告的文本与截图的相似度
    float getImageAndTextSimilarity(Integer reportId) throws IOException, ModelException, TranslateException;
}
