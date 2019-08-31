package com.x3110.learningcommunity.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.x3110.learningcommunity.model.Report;
import com.x3110.learningcommunity.result.Result;
import com.x3110.learningcommunity.result.ResultCode;
import com.x3110.learningcommunity.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Primary
public class ReportServiceImpl implements ReportService{
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    UserService userService;

    @Override
    public Report getReportById(String id) {
        Query query=new Query(Criteria.where("id").is(id));
        Report report=mongoTemplate.findOne(query,Report.class);
        return report;
    }

    @Override
    public Result addReport(Report report) {
        report.setCreatedDate(LocalDateTime.now());
        mongoTemplate.insert(report);
        return ResultFactory.buildSuccessResult("举报成功！");
    }

    private UpdateResult setDone(Report report){
        Query query = new Query(Criteria.where("id").is(report.getId()));
        Update update = new Update();
        update.set("done", 1);
        return mongoTemplate.updateFirst(query, update, Report.class);

    }

    @Override
    public Result valuableReport(String reportId) {
        Report report = getReportById(reportId);
        if(report == null)return ResultFactory.buildFailResult(ResultCode.NOT_FOUND);

        setDone(report);//标志为已处理
        return ResultFactory.buildSuccessResult("成功处理举报！");
    }

    @Override
    public DeleteResult deleteReport(String id) {
        return mongoTemplate.remove(new Query(Criteria.where("id").is(id)),"report");
    }
}
