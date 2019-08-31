package com.x3110.learningcommunity.controller;

import com.x3110.learningcommunity.model.Report;
import com.x3110.learningcommunity.model.ReportRepository;
import com.x3110.learningcommunity.result.Result;
import com.x3110.learningcommunity.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ReportController {
    @Autowired
    ReportService reportService;
    @Autowired
    ReportRepository reportRepository;

    @RequestMapping(value = "reportComment", method = RequestMethod.POST)
    public Result reportComment(@RequestBody Report report){
        return reportService.addReport(report);
    }

    @RequestMapping(value = "getAllReports", method = RequestMethod.GET)
    public List<Report> getAllReports(){
        return reportRepository.findAll();
    }

//    @RequestMapping(value = "valuableReport", method = RequestMethod.GET)
//    public Result valuableReport(String reportId){
//        return
//    }
}
