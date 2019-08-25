package com.x3110.learningcommunity.controller;

import com.x3110.learningcommunity.model.UploadFile;
import com.x3110.learningcommunity.result.Result;
import com.x3110.learningcommunity.result.ResultCode;
import com.x3110.learningcommunity.result.ResultFactory;
import com.x3110.learningcommunity.service.FileService;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileController {
    @Autowired
    FileService fileService;

    @CrossOrigin
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public Result uploadFile(@RequestParam(value = "image")MultipartFile file){
        if(file.isEmpty())
            return ResultFactory.buildFailResult(ResultCode.NotExist);//未找到上传图片

        String fileName = file.getOriginalFilename();
        try{
            UploadFile uploadFile = new UploadFile();
            uploadFile.setName(fileName);
            uploadFile.setCreatedDate(LocalDateTime.now());
            uploadFile.setContent(new Binary(file.getBytes()));
            uploadFile.setContentType(file.getContentType());
            uploadFile.setSize(file.getSize());

            UploadFile saveFile = null;
            saveFile = fileService.saveFile(uploadFile);
            String url = "http://localhost:8000/file/"+saveFile.getId();
        }catch (IOException e){
            e.printStackTrace();
            return ResultFactory.buildFailResult(ResultCode.FAIL);//上次失败
        }
        return ResultFactory.buildSuccessResult("上传成功");
    }

    @CrossOrigin
    @RequestMapping(value = "/file/image/{id}", method = RequestMethod.GET, produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] image(@PathVariable String id){
        byte[] data = null;
        UploadFile file = null;
        file = fileService.fineFile(id);
        if(file != null){
            data = file.getContent().getData();
        }
        return data;
    }
}
