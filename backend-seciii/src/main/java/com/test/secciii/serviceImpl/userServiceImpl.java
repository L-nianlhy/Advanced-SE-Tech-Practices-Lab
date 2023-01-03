package com.test.secciii.serviceImpl;

import com.test.secciii.entity.*;
import com.test.secciii.repository.*;
import com.test.secciii.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.print.attribute.DocAttributeSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class userServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private ApplicationRepository applicationRepository;
    @Resource
    private EmployeeRepository employeeRepository;
    @Resource
    private AdminRepository adminRepository;
    @Resource
    private DiaryRepository diaryRepository;
    @Resource
    private TaskRepository taskRepository;
    @Resource
    private RanklRepository ranklRepository;

    private static final Logger log = LoggerFactory.getLogger(userServiceImpl.class);

    @Override
    public Result<User> register(User user){
        String username = user.getUsername();
        String password = user.getPassword();
        String role = user.getUserRole();
        User users = userRepository.findByUsername(username);
        //log.info(users.getId().toString());
        if(users != null){
            return new Result<>(0,"用户名已存在",user);
        }
        user.setLoginFlag(1);
        userRepository.save(user);
        User userSaved = userRepository.findByUsername(username);
        if(role.equals("employee")){
            Employee employee=new Employee(userSaved.getId());
            employee.setProfessionalAbility("");
            employee.setTaskPreference("");
            employee.setTestDevice("");
            employee.setActivityLevel(0);
            employee.setAvgScore("0");
            employee.setFinishRate("0");
            employee.setSuppNum(0);
            employee.setReviewAbility("0");
            employee.setReportSimilarity("0");
            employee.setTotalScore("0");
            employeeRepository.save(employee);
        }
        if(role.equals("admin")){
            Admin admin=new Admin(userSaved.getId(),adminRepository.findById(1).get().getRankRule());
            adminRepository.save(admin);
        }
        return new Result<>(1,"账号注册成功",userSaved);
    }

    @Override
    public  Result<User> login(User user){
        String username = user.getUsername();
        String password = user.getPassword();
        User target = userRepository.findByUsername(username);
        //log.info(target.getId().toString());
        if(target.getLoginFlag().equals(0)){
            return new Result<>(0,"该用户已被封禁！",user);
        }
        if(target!=null){
            if(password.equals(target.getPassword())){
                long currentTime=System.currentTimeMillis();
                Date date = new Date(currentTime);
                int x = target.getId();
                Diary diary = new Diary();
                diary.setUid(x);
                diary.setLoginTime(date);
                diaryRepository.save(diary);
                refreshRank();
                return new Result<>(1,"登录成功!",target);
            }
            else {
                return new Result<>(0,"密码错误！",user);
            }
        }
        return new Result<>(0,"用户名不存在！",user);
    }

    @Override
    public Result<User> forbidden(Integer userId) {
        User target = userRepository.findById(userId).get();
        target.setLoginFlag(0);
        userRepository.save(target);
        return new Result<>(1,"封禁用户成功！",target);
    }

    @Override
    public Result<User> allowable(Integer userId) {
        User target = userRepository.findById(userId).get();
        target.setLoginFlag(1);
        userRepository.save(target);
        return new Result<>(1,"解封用户成功！",target);
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public Result<Application> signUp(Integer employeeId,Integer taskId) {
        if(applicationRepository.findByTaskIdAndEmployeeId(taskId,employeeId)!=null){return new Result<>(0,"已经报名过！",new Application());}
        Application application = new Application(taskId,employeeId,0);
        Task task = taskRepository.findById(taskId).get();
        if(task.getNeedWorkers()<=task.getSignedNum()){
            return new Result<>(0,"人数已满！",application);
        }
        Date currentTime = new Date(System.currentTimeMillis());
        if(currentTime.getTime()<task.getStartTime().getTime()){
            return new Result<>(0,"此测试还未开始！",application);
        }
        if(currentTime.getTime()>task.getEndTime().getTime()){
            return new Result<>(0,"此测试已结束！",application);
        }
        if(task.getSignedNum()==task.getNeedWorkers()){
            return new Result<>(0,"报名人数已满！",application);
        }
        Application applicationSaved = applicationRepository.save(application);
        task.setSignedNum(task.getSignedNum()+1);
        taskRepository.save(task);
        return new Result<>(1,"报名成功！",applicationSaved);
    }

    @Override
    public User getInfoByUserId(Integer UserId){
        User user = userRepository.findById(UserId).get();
        return user;
    }

    @Override
    public User updateUserSignature(Integer userId, String userSignature) {
        User user = userRepository.findById(userId).get();
        user.setUserSignature(userSignature);
        userRepository.save(user);
        return user;
    }

    @Override
    public List<Rankl> getRank() {
        List<Rankl> rankls = ranklRepository.findAll();
        return rankls;
    }

    @Override
    public List<Rankl> refreshRank() {
        List<Rankl> rankList = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();
        for(int n=0;n<employees.size()-1;n++){
            for(int j=0;j<employees.size()-1;j++){
                if(new Double(employees.get(j).getTotalScore())<new Double(employees.get(j+1).getTotalScore())){
                    Employee e = employees.get(j);
                    employees.set(j,employees.get(j+1));
                    employees.set(j+1,e);
                }
            }
        }
        ranklRepository.deleteAll();
        for(int i=0;i<Math.min(employees.size(),20);i++){
            Rankl r = new Rankl(employees.get(i).getEmployeeId(),employees.get(i).getTotalScore());
            r.setEmployeeName(
                    userRepository.findById(employees.get(i).getEmployeeId()).get().getUsername());
            rankList.add(r);
            ranklRepository.save(r);
        }
        return rankList;
    }

    @Override
    public Boolean hasSoonFinishTaskOrNot(Integer userId){
        List<Application> allMyTasks = applicationRepository.findByEmployeeIdAndSubmit(userId,0);
        Date currentTime = new Date(System.currentTimeMillis());
        for(int i =0;i<allMyTasks.size();i++){
            Task oneTask = taskRepository.findById(allMyTasks.get(i).getTaskId()).get();
            if(currentTime.getTime()<oneTask.getEndTime().getTime()){
                int days = (int) ((oneTask.getEndTime().getTime() - currentTime.getTime()) / (1000*3600*24));
                if(days<3){return true;}
            }
        }
        return false;
    }
}
