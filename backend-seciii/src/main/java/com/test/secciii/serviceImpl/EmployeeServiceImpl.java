package com.test.secciii.serviceImpl;

import com.test.secciii.entity.*;
import com.test.secciii.repository.*;
import com.test.secciii.service.EmployeeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.test.secciii.vo.EmployeeVo;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    private ScoreRepository scoreRepository;
    @Resource
    private DiscriptionRepository discriptionRepository;
    @Resource
    private EmployeeRepository employeeRepository;
    @Resource
    private ReportRepository reportRepository;
    @Resource
    private EvaluationRepository evaluationRepository;
    @Resource
    private SupplementRepository supplementRepository;
    @Resource
    private TaskRepository taskRepository;
    @Resource
    private ApplicationRepository applicationRepository;
    @Resource
    private DiaryRepository diaryRepository;
    @Resource
    private RanklRepository ranklRepository;
    @Resource
    private UserRepository userRepository;


    @Override
    public EmployeeVo getInfoByEmployeeId(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();
        Integer activityLevel = getActivityLevelByEmployeeId(employeeId);
        Double avgScore = getAvgScoreByEmployeeId(employeeId);
        Double finishRate = getFinishRateByEmployeeId(employeeId);
        Integer suppNum = getSuppNumByEmployeeId(employeeId);
        Integer suppScore = suppNum*5;
        Double reviewAbility = getReviewAbilityByEmployeeId(employeeId);
        Double reportSimilarity = getReportSimilarityByEmployeeId(employeeId);
        //总分根据employee的六项能力百分制平均后计算
        Double totalScore;
        if(reportRepository.findByEmployeeIdAndFinish(employeeId,1).size()==0)
            totalScore = (activityLevel*1.0 + avgScore*20 + finishRate*100 +
                suppScore + reviewAbility)/5;
        else totalScore = (activityLevel*1.0 + avgScore*20 + finishRate*100 +
                suppScore + reviewAbility + (1-reportSimilarity)*100)/6;

        employee.setActivityLevel(activityLevel);
        employee.setAvgScore(avgScore.toString());
        employee.setFinishRate(finishRate.toString());
        employee.setSuppNum(suppNum);
        employee.setReviewAbility(reviewAbility.toString());
        employee.setReportSimilarity(reportSimilarity.toString());
        employee.setTotalScore(totalScore.toString());
        employeeRepository.save(employee);
        List<String> professionalAbility = new ArrayList<>();
        List<String> testDevice = new ArrayList<>();
        if(!employee.getProfessionalAbility().equals(""))
            professionalAbility = Arrays.asList(employee.getProfessionalAbility().split(","));
        if(!employee.getTestDevice().equals(""))
            testDevice = Arrays.asList(employee.getTestDevice().split(","));
        EmployeeVo employeeVo = new EmployeeVo(
                employee.getEmployeeId(),
                professionalAbility,
                employee.getTaskPreference(),
                testDevice,
                activityLevel,
                avgScore,
                finishRate,
                suppNum,
                reviewAbility,
                reportSimilarity,
                totalScore);
        return employeeVo;
    }

    @Override
    public Result<Employee> setEmployeeInfo(Integer employeeId, String professionalAbility, String taskPreference, String testDevice) {
        Employee employee=employeeRepository.findById(employeeId).get();
        employee.setProfessionalAbility(professionalAbility);
        employee.setTestDevice(testDevice);
        employee.setTaskPreference(taskPreference);
        employeeRepository.save(employee);
        return new Result<>(1,"设置成功！",employee);
    }

    @Override
    public Integer getRankByEmployeeId(Integer employeeId) {
        List<Rankl> rankls=ranklRepository.findAll();
        if(ranklRepository.findByEmployeeId(employeeId).size()!=0){
            for(int i=0;i<rankls.size();i++){
                if(rankls.get(i).getEmployeeId()==employeeId){
                    return i+1;
                }
            }
            return 0;
        }else{
            return 0;
        }
    }

    //工人的活跃等级为  登录天数/总天数+报告数+评分数+评论数+协作数
    public Integer getActivityLevelByEmployeeId(Integer employeeId) {
        int result=0;
        result = (int)(loginRate(employeeId)*50.0
                + reportRepository.findByEmployeeIdAndFinish(employeeId,1).size()* 8.0
                + scoreRepository.findByEmployeeId(employeeId).size()*1.0
                + discriptionRepository.findByEmployeeId(employeeId).size()*2.0
                + supplementRepository.findByEmployeeId(employeeId).size()*5.0);
        if(result>0 && result<100) return result;
        else if(result<=0) return 0;
        else return 100;
    }

    //计算自首次登录起该用户登录天数/总天数
    public Double loginRate(Integer employeeId){
        List<Diary> diaries = diaryRepository.findByUid(employeeId);  //该工人的所有登录记录
        Long t = diaries.get(0).getLoginTime().getTime()/(1000*3600*24); //登录的第一天
        Integer loginDays = 0;
        Integer totalDays = (int)
                (diaries.get(diaries.size()-1).getLoginTime().getTime()/(1000*3600*24)-t)+1;
        for(int i=0;i<diaries.size();i++){
            Long ti=diaries.get(i).getLoginTime().getTime();
            if(ti>=t*1000*3600*24  && ti-t*1000*3600*24<1000*3600*24){
                t=t+1;
                loginDays++;
            }
        }
        return loginDays*1.0/totalDays;
    }

    public Double getAvgScoreByEmployeeId(Integer employeeId) {
        List<Report> reports = reportRepository.findByEmployeeIdAndFinish(employeeId,1);
        Double Avg = 0.0;
        for(int i=0;i<reports.size();i++){
            Avg = (Avg * i + new Double(reports.get(i).getAvgScore()))/(i+1);
        }
        return Avg;
    }

    public Double getFinishRateByEmployeeId(Integer employeeId){
        List<Application> applications = applicationRepository.findByEmployeeId(employeeId);
        List<Integer> ApplTasks = new ArrayList<>();
        Integer FiniNum = 0;
        if(applications.size()!=0){
            for(int i=0;i<applications.size();i++){
                    ApplTasks.add(applications.get(i).getTaskId());
            }
        }else return 0.0;
        //在所有已报名的任务里，提交报告总数/总任务数即为完成率
        if(ApplTasks.size()!=0){
            for(int i=0;i<ApplTasks.size();i++){
                FiniNum += reportRepository.findByTaskIdAndEmployeeIdAndFinish(
                        ApplTasks.get(i),employeeId,1).size();
            }
            return FiniNum*1.0/ApplTasks.size();
        }
        else return 0.0;
    }



    public Integer getSuppNumByEmployeeId(Integer employeeId) {
        return supplementRepository.findByEmployeeId(employeeId).size();
    }

    public Double getReviewAbilityByEmployeeId(Integer employeeId){
        List<Score> scores = scoreRepository.findByEmployeeId(employeeId);
        Double sum=0.0;
        if(scores.size()!=0) {
            for (int i = 0; i < scores.size(); i++) {
                Integer reportId = scores.get(i).getReportId();
                Report report = reportRepository.findById(reportId).get();
                Double avgscore = new Double(report.getAvgScore());
                Integer myscore = scores.get(i).getScore();
                sum += Math.abs(avgscore - new Double(myscore));
            }
            sum = sum / scores.size();

            String str = String.format("%.2f", sum);
            double res = Double.parseDouble(str);
            return 20 * (5.0 - res);
        }
        else return 0.0;
    }

    public Double getReportSimilarityByEmployeeId(Integer employeeId) {
        List<Double> similarities = new ArrayList<>();
        List<Report> reports = reportRepository.findByEmployeeIdAndFinish(employeeId,1);
        if(reports.size()!=0){
        for(int i=0;i<reports.size();i++){
            similarities.add(getAvg(getSimilarityByReportId(reports.get(i).getId())));
        }
        }
        return getAvg(similarities);
    }

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

    public Double getAvg(List<Double> list){
        Double Avg=0.0;
        if(list.size()!=0) {
            for (int i = 0; i < list.size(); i++) {
                Avg = Avg + list.get(i);
            }
        }
        if(list.size()!=0) Avg=Avg/list.size();
        return Avg;
    }

}
