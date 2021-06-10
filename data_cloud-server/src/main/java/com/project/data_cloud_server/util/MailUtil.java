package com.project.data_cloud_server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {
    @Autowired
    JavaMailSender javaMailSender;
    public  void sendMail(String mailFrom,String mailTo,String subject,String text){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(mailTo);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);

    }

    public String generateCode(){
        StringBuffer buffer=new StringBuffer();
        for(int i=1;i<=6;i++){
            buffer.append((int)(Math.random()*10));
        }
        return buffer.toString();
    }
}
