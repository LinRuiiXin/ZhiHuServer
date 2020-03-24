package com.sz.ZhiHu.service;

import com.sz.ZhiHu.dao.UserDao;
import com.sz.ZhiHu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public boolean mailHasRegistered(String mail) {
        Long userIdByEmail = userDao.getUserIdByMail(mail);
        if(userIdByEmail == null){
            return false;
        }else{
            return true;
        }
    }

    @Transactional
    @Override
    public User insertUser(User user) {
        userDao.insertUser(user);
        return user;
    }

    @Override
    public boolean nameHasRegistered(String userName) {
        return userDao.getUserIdByUserName(userName) == null ? false:true;
    }
    @Transactional
    @Override
    public User queryUserByMail(String mail) {
        return userDao.queryUserByMail(mail);
    }
    @Transactional
    @Override
    public User queryUserByMailPassword(String mail, String password) {
        return userDao.queryUserByMailPassword(mail,password);
    }

    @Override
    public boolean phoneHasRegistered(String phone) {
        return userDao.getUserIdByPhone(phone) == null?false:true;
    }

    @Override
    public void setPortraitFileNameById(Long id, String fileName) {
        userDao.setPortraitFileName(id,fileName);
    }
}
