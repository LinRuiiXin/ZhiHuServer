package com.sz.ZhiHu.dao;

import com.sz.ZhiHu.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    Long getUserIdByMail(String mail);
    void insertUser(User user);
    Long getUserIdByUserName(String userName);
    User queryUserByMail(String mail);
    User queryUserByMailPassword(String mail,String password);
    Long getUserIdByPhone(String phone);
    void setPortraitFileName(@Param("id") Long id, @Param("fileName") String fileName);
}
