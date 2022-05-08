package com.zhulin.dao;

import com.zhulin.entities.User;

/**
 * @Author:ZHULIN
 * @Description:
 */
public interface UserDao {
    /**
     * 根据用户名查找用户
     * @param uName
     * @return
     */
    User getUserByName(String uName);

    /**
     * 用户注册
     * @param user
     * @return
     */
    int signUpUser(User user);
}
