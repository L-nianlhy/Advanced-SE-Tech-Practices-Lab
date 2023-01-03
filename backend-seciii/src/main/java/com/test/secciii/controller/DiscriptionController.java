package com.test.secciii.controller;

import com.test.secciii.entity.Discription;
import com.test.secciii.entity.Result;
import com.test.secciii.entity.Score;
import com.test.secciii.po.DiscriptionPo;
import com.test.secciii.service.DiscriptionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("discription")
public class DiscriptionController {
    @Resource
    private DiscriptionService discriptionService;
    @PostMapping("/newDiscription")
    public Result<Discription> newDiscription(@RequestBody Discription discription){ return discriptionService.newDiscription(discription.getEmployeeId(),discription.getReportId(),discription.getDiscription());}
    @PostMapping("/getDiscriptionByReportId")
    public List<DiscriptionPo> getDiscription(@RequestParam Integer reportId){return  discriptionService.getDiscriptionByReportId(reportId);}
}
