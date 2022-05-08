package com.zhulin.service;

import com.zhulin.dto.SignUpParam;
import com.zhulin.entities.User;

/**
 * @Author:ZHULIN
 * @Description:
 */
public interface UserService {

    /**
     * 根据用户名生成随机数
     * @param uName
     * @return
     */
    String getRandomId(String uName);

    /**
     * 用户登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 用户注册
     * @param signUpParam
     * @return
     */
    Boolean signUpUser(SignUpParam signUpParam);
}
