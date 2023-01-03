package com.test.secciii.controller;

import com.test.secciii.entity.Result;
import com.test.secciii.entity.Supplement;
import com.test.secciii.service.SupplementService;
import com.test.secciii.vo.SupplementVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/supplement")
public class SupplementController {
    @Resource
    private SupplementService supplementService;
    @PostMapping("/completeSupplementInfo")
    public Result<Supplement> completeSupplementInfo(@RequestBody SupplementVo supplementVo){
        return supplementService.completeSupplementInfo(supplementVo);
    }
    @PostMapping("/getSupplementByReportId")
    public List<Supplement> getSupplementByReportId(@RequestParam Integer reportId){
        return supplementService.getSupplementByReportId(reportId);
    }
}
