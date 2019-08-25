package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.model.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@Primary
public class FileServiceImpl implements FileService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public UploadFile saveFile(UploadFile uploadFile) {
        UploadFile saveFile = mongoTemplate.save(uploadFile);
        return saveFile;
    }

    @Override
    public UploadFile fineFile(String id) {
        return mongoTemplate.findById(id, UploadFile.class);
    }
}
