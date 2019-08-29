package com.x3110.learningcommunity.util;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class MailUtil {
    private JavaMailSenderImpl senderImpl;

    public void setSenderImpl(JavaMailSenderImpl senderImpl){
        this.senderImpl=senderImpl;
    }

    private SimpleMailMessage mailMessage;

    public void setMailMessage(SimpleMailMessage mailMessage) {
        this.mailMessage = mailMessage;
    }

    private Properties prop;

    public void setProp(Properties prop) {
        this.prop = prop;
    }

    public boolean sendMail(String to,String text){
        System.out.println("sendMail...util...");
        try{
            senderImpl.setHost("smtp.qq.com");
            mailMessage.setTo(to);
            mailMessage.setFrom("自己的邮箱");
            mailMessage.setSubject("主题");
            mailMessage.setText("内容"+text);

            senderImpl.setUsername("自己的邮箱");
            senderImpl.setPassword("密码");

            prop.put("mail.smtp.auth","true");
            prop.put("mail.smtp.timeout",25000);
            senderImpl.setJavaMailProperties(prop);

            senderImpl.send(mailMessage);
            System.out.println("发送邮箱成功");
            return true;
        }catch (Exception e){
            System.out.println("发送邮箱失败");
            return false;
        }
    }
}
