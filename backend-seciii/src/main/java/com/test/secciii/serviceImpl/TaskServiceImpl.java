package com.test.secciii.serviceImpl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.secciii.entity.*;
import com.test.secciii.repository.*;
import com.test.secciii.service.TaskService;
import com.test.secciii.vo.EmployeeVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private AdminRepository adminRepository;
    @Resource
    private EmployeeRepository employeeRepository;
    @Resource
    private TaskRepository taskRepository;
    @Resource
    private ApplicationRepository applicationRepository;
    @Resource
    private ReportRepository reportRepository;
    @Resource
    private ProjectRepository projectRepository;
    @Override
    public Result<Task> newTask(Task task){
        Project project = projectRepository.findById(task.getProjectId()).get();
        project.setSubtasksNum(project.getSubtasksNum()+1);
        projectRepository.save(project);
        //修改任务类型为中文
        String type_int = task.getTaskType();
        if(type_int.equals("1")){task.setTaskType("功能测试");}
        else if (type_int.equals("2")){task.setTaskType("性能测试");}
        else if (type_int.equals("3")){task.setTaskType("其他");}
        else {return new Result<>(0,"创建失败，未设置测试类型！",task);}

        if(task.getStartTime().getTime()>task.getEndTime().getTime()){
            return new Result<>(0,"开始时间不能晚于结束时间！",task);
        }
        //暂存任务
        Task taskSaved = taskRepository.save(task);

        //设置文件的存储路径
        String filePath = "seciiiFile//";
        filePath = filePath + taskSaved.getId();

        //新建测试文件夹
        File taskFile = new File(filePath);
        taskFile.setWritable(true);
        taskFile.setExecutable(true);
        taskFile.setReadable(true);
        boolean x=true;
        if(!taskFile.exists()){x=taskFile.mkdirs();}

        //给数据库中任务的文件路径赋值
        taskSaved.setFilePath(filePath);
        String deviceType=task.getTestDevice();
        taskSaved.setTestDevice(deviceType);
        taskSaved.setAvailable(1);
        taskSaved.setSignedNum(0);
        //更新任务数据
        taskRepository.save(taskSaved);
        String s = "true";
        if(!x){s="false";}
        return new Result<>(1,"创建成功！"+ s,taskSaved);
    }

    //根据不同的用户获取动态广场内的任务列表
    @Override
    public List<Task> getAllTasks(Integer userId) {
        User user = userRepository.findById(userId).get();
        String rankRule = adminRepository.findById(1).get().getRankRule();
        List<Task> tasks = taskRepository.findAll();
        Task task;
        if(user.getUserRole().equals("employee")){
            Employee employee = employeeRepository.findById(userId).get();
            EmployeeVo employeeVo = new EmployeeVo(
                    employee.getEmployeeId(),
                    Arrays.asList((employee.getProfessionalAbility()+"").split(",")),
                    employee.getTaskPreference(),
                    Arrays.asList((employee.getTestDevice()+"").split(","))
            );
            //按照工人的任务偏好推荐，如果任务测试类型与工人偏好一致，则优先推荐
            if(rankRule.equals("测试偏好")){
                int n=0;
                for(int i=0;i<tasks.size();i++){
                    //遍历任务列表,找寻是否有符合工人任务偏好的任务。若有，则将其前置到索引n。
                    if(tasks.get(i).getTaskType().equals(employeeVo.getTaskPreference())){
                        //将索引n处和索引i处的task交换
                        task=tasks.get(n);
                        tasks.set(n,tasks.get(i));
                        tasks.set(i,task);
                        n++;
                    }
                }
            }
            //按照测试设备推荐,如果任务要求设备在工人测试设备范围内，则优先推荐
            else if(rankRule.equals("测试设备")){
                int k=0;int n=0;
                //遍历任务列表，找寻是否有符合工人测试设备的任务，如果有，则将其前置。
                for(int i=0;i<tasks.size();i++){
                    for(int j=0;j<employeeVo.getTestDevice().size();j++){
                        if(employeeVo.getTestDevice().get(j).equals(tasks.get(i).getTestDevice())) k++;
                    }
                    if(k!=0){
                        //将索引n处和索引i处的task交换
                        task=tasks.get(n);
                        tasks.set(n,tasks.get(i));
                        tasks.set(i,task);
                        n++;
                    }
                    k=0;
                }
            }
            //按照任务内容推荐，如果任务内容与工人最近一次完成的任务相似，则优先推荐
            else if(rankRule.equals("测试内容")){
                List<Report> reports = reportRepository.findAll();
                List<Double> Similarity = new ArrayList<>();
                Integer taskId=0;
                double t;
                for(int i=0;i<reports.size();i++){//获取该工人最近一次完成的任务
                    if(reports.get(i).getEmployeeId()==userId){
                        taskId = reports.get(i).getTaskId();
                    }
                }
                for(int i=0;i<tasks.size();i++){
                    Similarity.add(getSimilarity(tasks.get(i).getId(),taskId));
                }
                for (int i=0;i<tasks.size();i++) {
                    for (int j = 0; j < tasks.size() - 1; j++) {
                        if (Similarity.get(j) < Similarity.get(j + 1)) {
                            t = Similarity.get(j);
                            Similarity.set(j, Similarity.get(j + 1));
                            Similarity.set(j + 1, t);
                            task = tasks.get(j);
                            tasks.set(j, tasks.get(j + 1));
                            tasks.set(j + 1, task);
                        }
                    }
                }
            }
            //按照历史任务类型推荐，将完成类型多的任务排在前面推荐
            else if(rankRule.equals("历史测试")&&
                    reportRepository.findByEmployeeIdAndFinish(userId,1).size()!=0){
                List<Report> reports = reportRepository.findByEmployeeIdAndFinish(userId,1);
                //historyTasks为此工人已完成的任务列表
                List<Task> historyTasks = new ArrayList<>();
                List<Integer> initialTypeNum = new ArrayList<>(Arrays.asList(0,0,0));
                List<Integer> typeNum = initialTypeNum;
                int F=typeNum.get(0),P=typeNum.get(1),E=typeNum.get(2);
                //typeNum三个参数分别为功能测试数，性能测试数，其他数
                for(int i=0;i<reports.size();i++) {
                    historyTasks.add(taskRepository.findById((reports.get(i).getTaskId())).get());
                }
                //计算已完成任务的各个任务类型的数目
                for(int i=0;i<historyTasks.size();i++){
                    String taskiType = historyTasks.get(i).getTaskType(); //第i个任务的类型
                    if(taskiType.equals("功能测试")){
                        F++;
                        typeNum.set(0, F);
                    }
                    else if(taskiType.equals("性能测试")){
                        P++;
                        typeNum.set(1, P);
                    }
                    else if(taskiType.equals("其他")){
                        E++;
                        typeNum.set(2, E);
                    }
                }
                List<Task> allTask = new ArrayList<>();
                for(int i=0;i<tasks.size();i++){
                    allTask.add(tasks.get(i));
                }
                tasks.clear();
                String type1="";
                String type2="";
                String type3="";
                if(typeNum.get(0)>=typeNum.get(1)&&typeNum.get(1)>=typeNum.get(2)){
                    type1 = "功能测试";
                    type2 = "性能测试";
                    type3 = "其他";
                }
                if(typeNum.get(0)>=typeNum.get(2)&&typeNum.get(2)>=typeNum.get(1)){
                    type1 = "功能测试";
                    type2 = "其他";
                    type3 = "性能测试";
                }
                if(typeNum.get(1)>=typeNum.get(0)&&typeNum.get(0)>=typeNum.get(2)){
                    type1 = "性能测试";
                    type2 = "功能测试";
                    type3 = "其他";
                }
                if(typeNum.get(1)>=typeNum.get(2)&&typeNum.get(2)>=typeNum.get(0)){
                    type1 = "性能测试";
                    type2 = "其他";
                    type3 = "功能测试";
                }
                if(typeNum.get(2)>=typeNum.get(0)&&typeNum.get(0)>=typeNum.get(1)){
                    type1 = "其他";
                    type2 = "功能测试";
                    type3 = "性能测试";
                }
                if(typeNum.get(2)>=typeNum.get(1)&&typeNum.get(1)>=typeNum.get(0)){
                    type1 = "其他";
                    type2 = "性能测试";
                    type3 = "功能测试";
                }
                for(int i=0;i<allTask.size();i++){
                    if(allTask.get(i).getTaskType().equals(type1)){
                        tasks.add(allTask.get(i));
                    }
                }
                for(int i=0;i<allTask.size();i++){
                    if(allTask.get(i).getTaskType().equals(type2)){
                        tasks.add(allTask.get(i));
                    }
                }
                for(int i=0;i<allTask.size();i++){
                    if(allTask.get(i).getTaskType().equals(type3)){
                        tasks.add(allTask.get(i));
                    }
                }


            }
        }
        for(int i=0;i<tasks.size();i++){
            long currentTime = System.currentTimeMillis();
            long endTime = tasks.get(i).getEndTime().getTime();
            if(currentTime<endTime){
                tasks.get(i).setAvailable(1);
            }else{
                tasks.get(i).setAvailable(0);
            }
            taskRepository.save(tasks.get(i));
        }
        return tasks;
    }

    public List<Task> getSimilarTaskByEmployeeId(Integer employeeId){
        return null;
    }

    public double getSimilarity(Integer taskId1,Integer taskId2){
        Task task1 = taskRepository.findById(taskId1).get();
        Task task2 = taskRepository.findById(taskId2).get();
        Double similarOfName = Similarity.getSimilarity(
            task1.getTaskName(), task2.getTaskName()
        );
        Double similarOfIntroduction = Similarity.getSimilarity(
            task1.getIntroduction(), task2.getIntroduction()
        );
        Double similarOfTestDevice;
        if(task1.getTestDevice().equals(task2.getTestDevice())){similarOfTestDevice=1.0;}
        else similarOfTestDevice = 0.0;
        Double similarOfTaskType;
        if(task1.getTaskType().equals(task2.getTaskType())){similarOfTaskType=1.0;}
        else similarOfTaskType = 0.0;
        Double result = (similarOfName*1 + similarOfIntroduction*5 +
                similarOfTestDevice*3 + similarOfTaskType*3)/12;
        return result;
    }

    @Override
    public List<Task> getTasksByUserId(Integer uid) {
        return taskRepository.findByEmployerId(uid);
    }

    @Override
    public List<Task> getNotSubmitTasks(Integer employeeId) {
        List<Application> applicationsSigned = applicationRepository.findByEmployeeIdAndSubmit(employeeId,0);
        List<Task> result = new ArrayList<>();
        for(int i=0;i<applicationsSigned.size();i++){
            Task task = taskRepository.findById(applicationsSigned.get(i).getTaskId()).get();
            result.add(task);
        }

        return result;
    }

    @Override
    public List<Task> getSubmitTasks(Integer employeeId) {
        List<Application> applicationsSigned = applicationRepository.findByEmployeeIdAndSubmit(employeeId,1);
        List<Task> result = new ArrayList<>();
        for(int i=0;i<applicationsSigned.size();i++){
            Task task = taskRepository.findById(applicationsSigned.get(i).getTaskId()).get();
            result.add(task);
        }
        return result;
    }

    @Override
    public List<Integer> getSignedAndTotalNum(Integer taskId) {
        List<Integer> result = null;
        result.add(applicationRepository.findByTaskIdAndSubmit(taskId,1).size());
        result.add(taskRepository.findById(taskId).get().getNeedWorkers());
        return result;
    }

    @Override
    public Boolean getAvailableOrNotByTaskId(Integer taskId) {
        long currentTime=System.currentTimeMillis();
        long endTime=taskRepository.findById(taskId).get().getEndTime().getTime();
        if(currentTime<endTime) return true;
        else return false;
    }

    @Override
    public List<Task> getFTaskByEmployerId(Integer employerId) {
        return taskRepository.findByEmployerIdAndTaskType(employerId,"功能测试");
    }

    @Override
    public List<Task> getPTaskByEmployerId(Integer employerId) {
        return taskRepository.findByEmployerIdAndTaskType(employerId,"性能测试");
    }

    @Override
    public List<Task> getEndTaskByEmployerId(Integer employerId) {
        return taskRepository.findByEmployerIdAndAvailable(employerId,0);
    }

    @Override
    public List<Task> getIntestTaskByEmployerId(Integer employerId) {
        return taskRepository.findByEmployerIdAndAvailable(employerId,1);
    }

    @Override
    public List<Task> getTaskByEmployeeIdInOrder(Integer employeeId) {
        List<Application> allMyTasks = applicationRepository.findByEmployeeId(employeeId);
        List<Task> tasks = new ArrayList<>();
        Task[] arr = new Task[allMyTasks.size()];
        for(int i =0;i<allMyTasks.size();i++){
            Task oneTask = taskRepository.findById(allMyTasks.get(i).getTaskId()).get();
            arr[i]=oneTask;
        }
        for(int i =0;i<arr.length-1;i++){
            for(int j=0;j<arr.length-1;j++){
                if(arr[j].getEndTime().getTime()>arr[j+1].getEndTime().getTime()){
                    Task temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        for(int i =0;i<arr.length;i++){
            tasks.add(arr[i]);
        }
        return tasks;
    }
}