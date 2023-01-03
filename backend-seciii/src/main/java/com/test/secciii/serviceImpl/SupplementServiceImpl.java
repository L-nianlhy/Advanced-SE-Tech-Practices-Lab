package com.test.secciii.serviceImpl;

import com.test.secciii.entity.Result;
import com.test.secciii.entity.Supplement;
import com.test.secciii.repository.SupplementRepository;
import com.test.secciii.service.SupplementService;
import com.test.secciii.vo.SupplementVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@Service
public class SupplementServiceImpl implements SupplementService {
    @Resource
    private SupplementRepository supplementRepository;

    @Override
    public Result<Supplement> completeSupplementInfo(SupplementVo supplementVo){
        Integer employeeId = supplementVo.getEmployeeId();
        Integer reportId = supplementVo.getReportId();
        String supplementName = supplementVo.getSupplementName();
        String defectDescription = supplementVo.getDefectDescription();
        String recurrenceSteps = supplementVo.getRecurrenceSteps();
        String deviceType = supplementVo.getDeviceType();
        Supplement supplementNew = new Supplement(employeeId,reportId);
        Supplement supplementSaved = supplementRepository.save(supplementNew);

        String screenshotsPath = "seciiiScreenshots//";
        screenshotsPath = screenshotsPath + reportId + File.separator + supplementSaved.getId();

        File supplementFile = new File(screenshotsPath);
        supplementFile.setWritable(true);
        supplementFile.setReadable(true);
        supplementFile.setExecutable(true);
        supplementFile.setWritable(true,false);
        if(!supplementFile.exists()){boolean res = supplementFile.mkdirs();}

        supplementSaved.setScreenshotsPath(screenshotsPath);
        supplementRepository.save(supplementSaved);
        supplementSaved.setSupplementName(supplementName);
        supplementSaved.setDefectDescription(defectDescription);
        supplementSaved.setRecurrenceSteps(recurrenceSteps);
        supplementSaved.setDeviceType(deviceType);
        supplementRepository.save(supplementSaved);

        return new Result<>(1,"提交成功！",supplementSaved);
    }

    @Override
    public List<Supplement> getSupplementByReportId(Integer reportId){
        return supplementRepository.findByReportId(reportId);
    }
}
