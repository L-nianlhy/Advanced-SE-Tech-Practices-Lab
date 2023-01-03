package com.test.secciii.service;

import com.test.secciii.entity.Discription;
import com.test.secciii.entity.Result;
import com.test.secciii.po.DiscriptionPo;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface DiscriptionService {
    Result<Discription> newDiscription(Integer employeeId, Integer reportId, String description);
    List<DiscriptionPo> getDiscriptionByReportId(Integer reportId);
}
