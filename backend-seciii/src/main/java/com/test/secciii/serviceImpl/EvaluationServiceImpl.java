package com.test.secciii.serviceImpl;

import com.test.secciii.entity.Evaluation;
import com.test.secciii.entity.Result;
import com.test.secciii.repository.EvaluationRepository;
import com.test.secciii.service.EvaluationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private EvaluationRepository evaluationRepository;

    @Override
    public Result<Evaluation> newEvaluation(Integer employeeId, Integer reportId, Integer score, String description) {
        Evaluation evaluation = new Evaluation(employeeId,reportId,score,description);
        if(evaluation.getDiscription().equals("")){
            return new Result<>(0,"请填写评分描述！",evaluation);
        }
        //如果某一工人已经对某一报告评分过，那么新建的结果将覆盖原来的评价
        if(evaluationRepository.findByEmployeeIdAndReportId(employeeId,reportId).size()==0)
            evaluation = evaluationRepository.save(evaluation);
        else {
            evaluation = evaluationRepository.findByEmployeeIdAndReportId(employeeId,reportId).get(0);
            evaluation.setScore(score);
            evaluation.setDiscription(description);
            evaluation = evaluationRepository.save(evaluation);
        }
        return new Result<>(1,"评价成功！",evaluation);
    }

    @Override
    public List<Evaluation> getEvaluationByReportId(Integer reportId) {
        return evaluationRepository.findByReportId(reportId);
    }


}
