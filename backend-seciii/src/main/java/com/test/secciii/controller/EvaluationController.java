package com.test.secciii.controller;

import com.test.secciii.entity.Evaluation;
import com.test.secciii.entity.Result;
import com.test.secciii.service.EvaluationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {
    @Resource
    private EvaluationService evaluationService;
    @PostMapping("/newEvaluation")
    public Result<Evaluation> newEvaluation(@RequestBody Evaluation evaluation){ return evaluationService.newEvaluation(evaluation.getEmployeeId(),evaluation.getReportId(),evaluation.getScore(),evaluation.getDiscription());}
    @PostMapping("/getEvaluationByReportId")
    public List<Evaluation> getEvaluationByReportId(@RequestParam Integer reportId){ return evaluationService.getEvaluationByReportId(reportId);}
}
