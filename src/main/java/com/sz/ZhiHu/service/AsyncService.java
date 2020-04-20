package com.sz.ZhiHu.service;

import com.sz.ZhiHu.dao.TypeRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Async
public class AsyncService {
    @Autowired
    JavaMailSenderImpl mailSender;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    ClassifyQuestionService classifyQuestionService;
    @Autowired
    TypeRecordDao typeRecordDao;
    /*
        异步发送邮件任务，发送成功后，将邮箱和验证码存入redis（60s有效），如果连续两次发送，以最后一次送达的为准
     */
    public void sendMail(String status,String mail,String code,String describe){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("ZhiHu");
            message.setText(describe+":\n"+code);
            message.setFrom("l18018749312@sohu.com");
            message.setTo(mail);
            mailSender.send(message);
        }catch (Exception e){
            return;
        }
        if (redisTemplate.hasKey(mail)) {
            redisTemplate.boundValueOps(status+mail).set(code,60,TimeUnit.SECONDS);
        }else{
            redisTemplate.opsForValue().set(status+mail,code,60,TimeUnit.SECONDS);
        }
    }
    public void setQuestionType(Long questionId,String typeStr){
        String[] split = typeStr.split("-");
        for(String typeId : split){
            classifyQuestionService.setQuestionType(questionId,Long.valueOf(typeId));
        }
    }
    public void recordUserBrowse(List<Long> ids,Long userId){
        ids.forEach(id->{
            typeRecordDao.addUserTypeRecord(id,userId);
        });
    }
}
