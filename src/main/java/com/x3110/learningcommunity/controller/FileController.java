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

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class FileController {
    @Autowired
    FileService fileService;

    /**
     * 通用的文件上传接口
     * @param file
     * @return Result
     */
    @CrossOrigin
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public Result uploadFile(@RequestBody MultipartFile file){
        if(file == null)
            return ResultFactory.buildFailResult(ResultCode.NotExist);//未找到上传文件

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
            return ResultFactory.buildSuccessResult(url);
        }catch (IOException e){
            e.printStackTrace();
            return ResultFactory.buildFailResult(e.getMessage());//上次失败
        }
    }

    /**
     * 图片获取接口（jpg,png)
     * @param id
     * @return byte[]
     */
    @CrossOrigin
    @RequestMapping(value = "/file/{id}", method = RequestMethod.GET, produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] image(@PathVariable String id){
        byte[] data = null;
        UploadFile file = null;
        file = fileService.fineFile(id);
        if(file != null){
            data = file.getContent().getData();
        }
        return data;
    }

    /**
     * 用户头像上传接口
     * @param file
     * @return Result
     */
//    @CrossOrigin
//    @RequestMapping(value = "/uploadAvater", method = RequestMethod.POST)
//    public Result uploadAvater(@RequestBody MultipartFile file){
//        if(file.isEmpty())
//            return ResultFactory.buildFailResult(ResultCode.NotExist);//未找到上传文件
//
//        String fileName = file.getOriginalFilename();
//        try{
//            UploadFile uploadFile = new UploadFile();
//            uploadFile.setName(fileName);
//            uploadFile.setCreatedDate(LocalDateTime.now());
//            uploadFile.setContent(new Binary(file.getBytes()));
//            uploadFile.setContentType(file.getContentType());
//            uploadFile.setSize(file.getSize());
//
//            UploadFile saveFile = null;
//            saveFile = fileService.saveFile(uploadFile);
//            String url = "http://localhost:8000/file/image/"+saveFile.getId();
//            return ResultFactory.buildSuccessResult(url);
//        }catch (IOException e){
//            e.printStackTrace();
//            return ResultFactory.buildFailResult(ResultCode.FAIL);//上次失败
//        }
//    }



}
