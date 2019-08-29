package com.x3110.learningcommunity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Random;

public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String sendPin(String toEmail) {
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
        return uuid;
    }
}
