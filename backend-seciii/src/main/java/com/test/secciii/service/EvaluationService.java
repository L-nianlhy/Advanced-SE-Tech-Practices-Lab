package com.test.secciii.service;

import com.test.secciii.entity.Evaluation;
import com.test.secciii.entity.Report;
import com.test.secciii.entity.Result;
import com.test.secciii.vo.ReportVo;

import java.util.List;

public interface EvaluationService {
    //新建评价
    Result<Evaluation> newEvaluation(Integer employeeId, Integer reportId, Integer score, String description);
    //获取某一报告的评价
    List<Evaluation> getEvaluationByReportId(Integer reportId);
}
