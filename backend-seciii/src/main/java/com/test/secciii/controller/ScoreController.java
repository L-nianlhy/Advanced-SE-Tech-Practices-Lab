package com.test.secciii.controller;

import com.test.secciii.entity.Evaluation;
import com.test.secciii.entity.Result;
import com.test.secciii.entity.Score;
import com.test.secciii.service.ScoreService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController {
    @Resource
    private ScoreService scoreService;
    @PostMapping("/newScore")
    public Result<Score> newScore(@RequestBody Score score){ return scoreService.newScore(score.getEmployeeId(),score.getReportId(),score.getScore());}
    @PostMapping("/getScoreByReportId")
    public List<Score> getScore(@RequestParam Integer reportId){return  scoreService.getScoreByReportId(reportId);}

}
