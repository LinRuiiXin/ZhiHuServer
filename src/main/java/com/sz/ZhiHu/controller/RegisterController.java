package com.sz.ZhiHu.controller;

import com.sz.ZhiHu.dto.SimpleDto;
import com.sz.ZhiHu.pojo.User;
import com.sz.ZhiHu.service.SyncService;
import com.sz.ZhiHu.service.UserService;
import com.sz.ZhiHu.util.SecurityCodeUtil;
import com.sz.ZhiHu.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Register")
public class RegisterController {
    @Autowired
    UserService userService;
    @Autowired
    SyncService syncService;
    @Autowired
    RedisTemplate redisTemplate;
    //根据邮箱地址判断当前用户是否已被注册，如果还未被注册，发送验证码，否则返回错误信息
    @GetMapping("/SecurityCode/{mail}")
    public SimpleDto sendMailSecurityCode(@PathVariable("mail") String mail){
        if(userService.mailHasRegistered(mail)){
            return new SimpleDto(false,"该账号已被注册",null);
        }else{
            syncService.sendMail("RegisterCode:",mail, SecurityCodeUtil.getSecurityCode(6),"感谢注册ZhiHu，您的验证码是");
            return new SimpleDto(true,null,null);
        }
    }
    //检查验证码
    @PostMapping("/SecurityCode/{mail}/{code}")
    public SimpleDto validateCode(@PathVariable("mail")String mail,@PathVariable("code") String code){
        Object o = redisTemplate.opsForValue().get("RegisterCode:"+mail);
        if(o == null){
            return new SimpleDto(false,"验证码过期或已失效",null);
        }else{
            String redisCode = (String) o;
            if(redisCode.equals(code.trim().toUpperCase())){
                return new SimpleDto(true,null,null);
            }else{
                return new SimpleDto(false,"验证码错误",null);
            }
        }
    }
    //接收一个User，校验密码、邮箱地址、电话号码，插入数据
    @PostMapping
    public SimpleDto register(@Valid @RequestBody User user, Errors errors){
        List<FieldError> fieldErrors = errors.getFieldErrors();
        Map<String,String> errorsMap = null;
        if(fieldErrors!=null && fieldErrors.size()!=0){
            errorsMap = new HashMap<>();
            for(FieldError error:fieldErrors){
                errorsMap.put(error.getField(),error.getDefaultMessage());
            }
            return new SimpleDto(false,"数据格式错误",errorsMap);
        }else{
            synchronized (this){
                if(!userService.nameHasRegistered(user.getUserName())){
                    if(!userService.mailHasRegistered(user.getMail())) {
                        userService.insertUser(user);
                        return new SimpleDto(true, null, null);
                    }else{
                        return new SimpleDto(false,"该邮箱已被注册",null);
                    }
                }else{
                    return new SimpleDto(false,"该用户名已被注册",null);
                }
            }
        }
    }
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(new UserValidator());
    }
}
