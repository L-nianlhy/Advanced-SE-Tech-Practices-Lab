package com.test.secciii.serviceImpl;

import com.test.secciii.entity.Discription;
import com.test.secciii.entity.Result;
import com.test.secciii.entity.User;
import com.test.secciii.po.DiscriptionPo;
import com.test.secciii.repository.DiscriptionRepository;
import com.test.secciii.repository.UserRepository;
import com.test.secciii.service.DiscriptionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DiscriptionServiceImpl implements DiscriptionService{
    @Resource
    private DiscriptionRepository discriptionRepository;
    @Resource
    private UserRepository userRepository;

    @Override
    public Result<Discription> newDiscription(Integer employeeId, Integer reportId, String description){
        Discription discription = new Discription(employeeId,reportId,description);
        if(discription.getDiscription().equals("")){
            return new Result<>(0,"请填写评论！",discription);
        }
        long currentTime=System.currentTimeMillis();
        Date date = new Date(currentTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
        String dayStr = sdf.format(date);
        discription.setTime(dayStr);
        discription = discriptionRepository.save(discription);
        return new Result<>(1,"评论成功！",discription);
    }

    @Override
    public List<DiscriptionPo> getDiscriptionByReportId(Integer reportId) {
        List<Discription> discriptions = discriptionRepository.findByReportId(reportId);
        List<DiscriptionPo> discriptionPos =new ArrayList<>();
        for(int i=0;i<discriptions.size();i++){
            Discription di = discriptions.get(i);
            DiscriptionPo discriptionPo = new DiscriptionPo(
                    di.getEmployeeId(),
                    userRepository.findById(di.getEmployeeId()).get().getUsername(),
                    userRepository.findById(di.getEmployeeId()).get().getUserRole(),
                    di.getReportId(), di.getDiscription(),
                    di.getTime()
            );
            discriptionPos.add(discriptionPo);
        }
        return discriptionPos;
    }
}
