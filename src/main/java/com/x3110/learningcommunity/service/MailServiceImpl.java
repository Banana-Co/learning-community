package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.result.Result;
import com.x3110.learningcommunity.result.ResultCode;
import com.x3110.learningcommunity.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Primary
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    public static final String SINGLE_EMAIL_REGEX = "(?:(?:[A-Za-z0-9\\-_@!#$%&'*+/=? ^`{|}~]|(?:\\\\[\\x00-\\xFF]?)|(?:\"[\\x00-\\xFF]*\"))+(?:\\.(?:(?:[A-Za-z0-9\\-_@!#$%&'*+/=?^`{|}~])|(?:\\\\[\\x00-\\xFF]?)|(?:\"[\\x00-\\xFF]*\"))+)*)@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+(?:(?:[A-Za-z0-9]*[A-Za-z][A-Za-z0-9]*)(?:[A-Za-z0-9-]*[A-Za-z0-9])?))";

    @Override
    public Result sendPin(String toEmail) {
        if(!validEmailAddress(toEmail))return ResultFactory.buildFailResult(ResultCode.INVALID_EMAIL_ADDRESS);
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("learningCom110@163.com");
        message.setTo(toEmail);
        message.setSubject("学习生活交流论坛登录验证");
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String uuid=new String();
        for(int i=0;i<6;i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            uuid += ch;
        }
        message.setText("您的注册验证码是："+uuid+"（区分大小写），"+"欢迎使用学习生活交流论坛");
        mailSender.send(message);
        return ResultFactory.buildSuccessResult(uuid);
    }

    Boolean validEmailAddress(String emailAddress){
        return emailAddress.matches(SINGLE_EMAIL_REGEX);
    }
}
