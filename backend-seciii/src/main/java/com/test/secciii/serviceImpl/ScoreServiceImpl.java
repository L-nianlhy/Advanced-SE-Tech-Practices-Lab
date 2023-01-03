package com.test.secciii.serviceImpl;

import com.test.secciii.entity.Evaluation;
import com.test.secciii.entity.Report;
import com.test.secciii.entity.Result;
import com.test.secciii.entity.Score;
import com.test.secciii.repository.ReportRepository;
import com.test.secciii.repository.ScoreRepository;
import com.test.secciii.service.ScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Resource
    private ScoreRepository scoreRepository;
    @Resource
    private ReportRepository reportRepository;

    @Override
    public Result<Score> newScore(Integer employeeId, Integer reportId, Integer score1){
        Score score = new Score(employeeId,reportId,score1);
        Report report = reportRepository.findById(reportId).get();
        //如果工人没有对该报告评分过，那么直接在数据库添加
        if(scoreRepository.findByEmployeeIdAndReportId(employeeId,reportId).size()==0)
            scoreRepository.save(score);
        //如果工人有对该报告评分过，那么将覆盖之前的评分
        else {
            Score target = scoreRepository.findByEmployeeIdAndReportId(employeeId,reportId).get(0);
            target.setScore(score1);
            scoreRepository.save(target);
        }
        List<Score> scores = scoreRepository.findByReportId(reportId);
        Double Avg=0.0;
        for(int i=0;i<scores.size();i++){
            Avg=(Avg*i+scores.get(i).getScore()*1.0)/(i+1);
        }
        report.setAvgScore(Avg.toString());
        reportRepository.save(report);
        return new Result<>(1,"评价成功！",score);
    }

    @Override
    public List<Score> getScoreByReportId(Integer reportId) {
        return scoreRepository.findByReportId(reportId);
    }

}
