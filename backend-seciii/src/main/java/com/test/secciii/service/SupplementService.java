package com.test.secciii.service;

import com.test.secciii.entity.Result;
import com.test.secciii.entity.Supplement;
import com.test.secciii.vo.SupplementVo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SupplementService {
    //新建、完成补充报告
    Result<Supplement> completeSupplementInfo(SupplementVo supplementVo);
    //获取某一报告的所有补充报告
    List<Supplement> getSupplementByReportId(Integer reportId);

}
