package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.result.Result;
import com.x3110.learningcommunity.result.ResultCode;
import com.x3110.learningcommunity.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@Primary
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    public static final String SINGLE_EMAIL_REGEX = "(?:(?:[A-Za-z0-9\\-_@!#$%&'*+/=?^`{|}~]|(?:\\\\[\\x00-\\xFF]?)|(?:\"[\\x00-\\xFF]*\"))+(?:\\.(?:(?:[A-Za-z0-9\\-_@!#$%&'*+/=?^`{|}~])|(?:\\\\[\\x00-\\xFF]?)|(?:\"[\\x00-\\xFF]*\"))+)*)@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+(?:(?:[A-Za-z0-9]*[A-Za-z][A-Za-z0-9]*)(?:[A-Za-z0-9-]*[A-Za-z0-9])?))";


    @Override
    public Result sendPin(String toEmail) {
        if(!validEmailAddress(toEmail))return ResultFactory.buildFailResult(ResultCode.INVALID_EMAIL_ADDRESS);
        String uuid=new String();
        try{
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg,true,"utf-8");
            helper.setFrom("learningCom110@163.com");//发件人
            helper.setTo(toEmail);//收件人
            helper.setSubject("学习生活交流论坛登录验证");//邮件标题
            //验证码
            String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            for(int i=0;i<6;i++) {
                char ch = str.charAt(new Random().nextInt(str.length()));
                uuid += ch;
            }
            String message = "<div><font face=\"黑体\"><font size=\"4\" style=\"\">您的注册验证码是：</font><b style=\"\"><u style=\"\"><font size=\"5\">" +
                    uuid+"</font></u></b><font size=\"4\" style=\"\">（区分大小写），欢迎使用学习生活交流论坛。</font></font></div>" +
                    "<div><font face=\"黑体\"><font size=\"4\" style=\"\">这个验证码将在您收到这封邮件5分钟后失效</font><font size=\"4\" style=\"\">。</font></font></div>";
            helper.setText(message, true);
            mailSender.send(msg);
        }catch (MessagingException e){
            return ResultFactory.buildFailResult(e.getMessage());
        }
//        SimpleMailMessage message=new SimpleMailMessage();
//        message.setFrom("learningCom110@163.com");
//        message.setTo(toEmail);
//        message.setSubject("学习生活交流论坛登录验证");
        return ResultFactory.buildSuccessResult(uuid);
    }

    Boolean validEmailAddress(String emailAddress){
        return emailAddress.matches(SINGLE_EMAIL_REGEX);
    }



}
