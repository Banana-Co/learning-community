package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.model.UploadFile;

public interface FileService {
    UploadFile saveFile(UploadFile uploadFile);

    UploadFile fineFile(String id);
}
