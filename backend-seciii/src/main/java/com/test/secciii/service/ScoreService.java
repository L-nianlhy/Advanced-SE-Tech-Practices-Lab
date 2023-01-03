package com.test.secciii.service;

import com.test.secciii.entity.Result;
import com.test.secciii.entity.Score;

import java.util.List;

public interface ScoreService {
    Result<Score> newScore(Integer employeeId, Integer reportId, Integer score);
    List<Score> getScoreByReportId(Integer reportId);

}
