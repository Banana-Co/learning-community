package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.model.Report;
import com.x3110.learningcommunity.result.Result;
import com.x3110.learningcommunity.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Primary
public class ReportServiceImpl implements ReportService{
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Result addReport(Report report) {
        report.setCreatedDate(LocalDateTime.now());
        mongoTemplate.insert(report);
        return ResultFactory.buildSuccessResult("举报成功！");
    }

    @Override
    public Result valuableReport(String reportId) {
        return null;
    }
}
