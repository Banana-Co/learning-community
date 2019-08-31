package com.x3110.learningcommunity.controller;

import com.mongodb.client.result.DeleteResult;
import com.x3110.learningcommunity.model.Report;
import com.x3110.learningcommunity.model.ReportRepository;
import com.x3110.learningcommunity.result.Result;
import com.x3110.learningcommunity.service.ReportService;
import com.x3110.learningcommunity.service.UserService;
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
    @Autowired
    UserService userService;

    @RequestMapping(value = "reportComment", method = RequestMethod.POST)
    public Result reportComment(@RequestBody Report report){
        return reportService.addReport(report);
    }

    @RequestMapping(value = "getAllReports", method = RequestMethod.GET)
    public List<Report> getAllReports(){
        return reportRepository.findAll();
    }

    @RequestMapping(value = "valuableReport", method = RequestMethod.GET)
    public Result valuableReport(String reportId){
        Report report =reportService.getReportById(reportId);
        userService.updatePrestige(report.getReportUsername(), 20);//举报人加声望
        userService.updatePrestige(report.getUsernameReported(), -50);//被举报人减声望
        return reportService.valuableReport(reportId);
    }

    @RequestMapping(value = "deleteReport", method = RequestMethod.GET)
    public DeleteResult deleteResult(String reportId){
        return reportService.deleteReport(reportId);
    }
}
