package com.sz.ZhiHu.service;

import com.sz.ZhiHu.pojo.User;

public interface UserService {
    public boolean mailHasRegistered(String mail);
    public void insertUser(User user);
    public boolean nameHasRegistered(String userName);
    public User queryUserByMail(String mail);
    public User queryUserByMailPassword(String mail,String password);
}
