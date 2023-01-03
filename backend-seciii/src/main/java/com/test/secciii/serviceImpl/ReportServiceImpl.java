package com.test.secciii.serviceImpl;

import ai.djl.ModelException;
import ai.djl.translate.TranslateException;
import com.test.secciii.entity.*;
import com.test.secciii.repository.*;
import com.test.secciii.service.ReportService;
import com.test.secciii.service.UserService;
import com.test.secciii.utils.SimilarityHelper;
import com.test.secciii.vo.ReportVo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {
    @Resource
    private ReportRepository reportRepository;
    @Resource
    private ApplicationRepository applicationRepository;
    @Resource
    private EvaluationRepository evaluationRepository;
    @Resource
    private ScoreRepository scoreRepository;
    @Resource
    private ScreenshotsRepository screenshotsRepository;
    @Resource
    private UserService userService;

    @Override
    public Result<Report> newReport(Integer employeeId, Integer taskId) {
        Report reportNew = new Report(employeeId,taskId,0);
        Report reportSaved = reportRepository.save(reportNew);

        String screenshotsPath = "seciiiScreenshots//";
        screenshotsPath = screenshotsPath + reportSaved.getId();

        File reportFile = new File(screenshotsPath);
        reportFile.setWritable(true);
        reportFile.setExecutable(true);
        reportFile.setReadable(true);
        if(!reportFile.exists()){reportFile.mkdirs();}
        reportSaved.setAvgScore("0.0");
        reportSaved.setScreenshotsPath(screenshotsPath);
        reportRepository.save(reportSaved);

        return new Result<>(1,"报告创建成功！",reportSaved);
    }

    @Override
    public Result<Report> completeReportInfo(ReportVo report) {
        String reportName = report.getReportName();
        String defectDescription = report.getDefectDescription();
        String recurrenceSteps = report.getRecurrenceSteps();
        String deviceType = report.getDeviceType();
        Report target = reportRepository.findById(report.getRId()).get();
        target.setReportName(reportName);
        target.setDefectDescription(defectDescription);
        target.setRecurrenceSteps(recurrenceSteps);
        target.setDeviceType(deviceType);
        target.setFinish(1);
        //提交报告时记录当前时间
        long currentTime = System.currentTimeMillis();
        Date currentDate = new Date(currentTime);
        target.setSubmitTime(currentDate);
        reportRepository.save(target);
        Integer taskId = target.getTaskId();
        Integer employeeId = target.getEmployeeId();
        Application application = applicationRepository.findByTaskIdAndEmployeeId(taskId,employeeId);
        application.setSubmit(1);
        applicationRepository.save(application);
        return new Result<>(1,"信息更新成功",target);
    }



    @Override
    public List<Report> getReportsByTaskId(Integer taskId) {
        return reportRepository.findByTaskIdAndFinish(taskId,1);
    }

    @Override
    public Double getAvgScoreByReportId(Integer reportId) {
            Double Avg = 0.0;
            if(scoreRepository.findByReportId(reportId).size()!=0){
                for(int i = 0; i < scoreRepository.findByReportId(reportId).size(); i++)
                    Avg = (Avg * i + scoreRepository.findByReportId(reportId).get(i).getScore() ) / (i+1);
            }
            return Avg;
    }

    @Override
    public List<Report> getSimilarByReportId(Integer reportId) {
        List<Report> SimilarRank = new ArrayList<Report>();
        List<Report> newSimilarRank = new ArrayList<Report>();
        List<Double> Similarity = new ArrayList<Double>();
        double t;
        Report report;
        Report report1 = reportRepository.findById(reportId).get();
        Integer taskId1 = report1.getTaskId();
        List<Report> reports = reportRepository.findByTaskIdAndFinish(taskId1,1);
        for(int i=0;i<reports.size();i++){
            //获取除了该报告本身的所有报告，以及相似度
            if (reports.get(i).getId()==reportId) continue;
            SimilarRank.add(reports.get(i));
            Similarity.add(getSimilarity(reportId,reports.get(i).getId()));
        }
        for(int i=0;i<SimilarRank.size();i++){
            for (int j=0;j<SimilarRank.size()-1;j++){
                if(Similarity.get(j)<Similarity.get(j+1)){
                    t=Similarity.get(j);
                    Similarity.set(j,Similarity.get(j+1));
                    Similarity.set(j+1,t);
                    report= SimilarRank.get(j);
                    SimilarRank.set(j,SimilarRank.get(j+1));
                    SimilarRank.set(j+1,report);
                }
            }
        }
        int flag=Similarity.size();
        for(int i=0;i<Similarity.size();i++){
            if (Similarity.get(i)<0.4) {
                flag=i;
                break;
            }
        }
        for (int i=0;i<flag;i++){
            newSimilarRank.add(SimilarRank.get(i));
        }
        return newSimilarRank;
    }
    //计算report2与report1的相似度
    public Double getSimilarity(Integer reportId1, Integer reportId2){
        Report report1=reportRepository.findById(reportId1).get();
        Report report2=reportRepository.findById(reportId2).get();
        Double simliarOfDefectDescription = Similarity.getSimilarity(
                report1.getDefectDescription(),
                report2.getDefectDescription()
        );
        Double simiarOfRecurrenceSteps = Similarity.getSimilarity(
                report1.getRecurrenceSteps(),
                report2.getRecurrenceSteps()
        );
        Double result = (simliarOfDefectDescription + simiarOfRecurrenceSteps)/2;
        return result;
    }

    @Override
    public List<Double> getSimilarityByReportId(Integer reportId){
        List<Report> SimilarRank = new ArrayList<Report>();
        List<Double> Similarity = new ArrayList<Double>();
        double t;
        Report report;
        Report report1 = reportRepository.findById(reportId).get();
        Integer taskId1 = report1.getTaskId();
        List<Report> reports = reportRepository.findByTaskIdAndFinish(taskId1,1);
        for(int i=0;i<reports.size();i++){
            if (reports.get(i).getId()==reportId) continue;
            SimilarRank.add(reports.get(i));
            Similarity.add(getSimilarity(reportId,reports.get(i).getId()));
        }
        for(int i=0;i<SimilarRank.size();i++){
            for (int j=0;j<SimilarRank.size()-1;j++){
                if(Similarity.get(j)<Similarity.get(j+1)){
                    t=Similarity.get(j);
                    Similarity.set(j,Similarity.get(j+1));
                    Similarity.set(j+1,t);
                    report= SimilarRank.get(j);
                    SimilarRank.set(j,SimilarRank.get(j+1));
                    SimilarRank.set(j+1,report);
                }
            }
        }
        return Similarity;
    }

    @Override
    public Result<Report> getReportsByTaskIdAndEmployeeId(Integer taskId, Integer employeeId) {
        List<Report> reports = reportRepository.findByTaskIdAndEmployeeIdAndFinish(taskId,employeeId,1);
        if(reports.size()==0){
            return new Result<>(0,"众包工人尚未完成报告",new Report());
        }
        return new Result<Report>(1,"该众包工人报告已完成",reports.get(0));
    }

    @Override
    public List<User> getEmployeesByTaskId(Integer taskId) {
        List<Application> applications = applicationRepository.findByTaskId(taskId);
        List<User> users = null;
        for(Application application:applications){
            users.add(userService.getInfoByUserId(application.getEmployeeId()));
        }
        return users;
    }

    @Override
    public float getImageAndTextSimilarity(Integer reportId) throws IOException, ModelException, TranslateException {
        List<Screenshots> screenshots = screenshotsRepository.findByReportId(reportId);
        Report report = reportRepository.findById(reportId).get();
        int len = screenshots.size();
        float sum = 0;
        for(int i=0;i<len;i++){
            sum = sum + SimilarityHelper.calculateSimilarity(screenshots.get(i).getScreenshotsPath(),report.getDefectDescription());
        }
        sum = sum/(len+1);
        return sum;
    }
}

