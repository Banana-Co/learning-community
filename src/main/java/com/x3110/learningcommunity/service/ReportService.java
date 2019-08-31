package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.model.Report;
import com.x3110.learningcommunity.result.Result;

public interface ReportService {
    Result addReport(Report report);
    Result valuableReport(String reportId);
}
