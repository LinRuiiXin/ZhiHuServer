package com.sz.ZhiHu.controller;

import com.sz.ZhiHu.dto.SimpleDto;
import com.sz.ZhiHu.po.User;
import com.sz.ZhiHu.service.AsyncService;
import com.sz.ZhiHu.service.UserService;
import com.sz.ZhiHu.util.SecurityCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Login")
public class LoginController {
    @Autowired
    AsyncService asyncService;
    @Autowired
    UserService userService;
    @Autowired
    StringRedisTemplate redisTemplate;
    //发送邮件任务
    @GetMapping("/SecurityCode/{mail}")
    public SimpleDto sendMailSecurityCode(@PathVariable("mail")String mail){
        //先判断是否存在用户
        if(userService.mailHasRegistered(mail)){
            //生成6位随机验证码
            String securityCode = SecurityCodeUtil.getSecurityCode(6);
            //执行异步发送邮件任务
            asyncService.sendMail("LoginCode:",mail,securityCode,"欢迎通过验证码登录ZhiHu,您的验证码是");
            return new SimpleDto(true,null,null);
        }else{
            return new SimpleDto(false,"该账号还未被注册",null);
        }
    }
    //免密码登录
    @GetMapping("/NoPassword/{mail}/{code}")
    public SimpleDto loginNoPassword(@PathVariable("mail") String mail,@PathVariable("code")String code){
        Object o = redisTemplate.opsForValue().get("LoginCode:" + mail);
        if(o != null){
            String codeInRedis = (String) o;
            if(codeInRedis.equals(code.trim().toUpperCase())){
                User user = userService.queryUserByMail(mail);
                if(user == null){
                    return new SimpleDto(false,"该账号不存在",null);
                }else{
                    return new SimpleDto(true,null,user);
                }
            }else{
                return new SimpleDto(false,"验证码错误",null);
            }
        }else{
            return new SimpleDto(false,"验证码过期或已失效",null);
        }
    }
    //带密码登录
    @GetMapping("/{mail}/{password}")
    public SimpleDto loginHasPassword(@PathVariable("mail")String mail,@PathVariable("password")String password){
        User user = userService.queryUserByMailPassword(mail, password);
        if(user == null){
            return new SimpleDto(false,"账号或密码错误",null);
        }else{
            return new SimpleDto(true,null,user);
        }
    }
}
