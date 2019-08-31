package com.x3110.learningcommunity.model;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface ReportRepository extends MongoRepository<Report, String> {
}