package com.zhulin.service.impl;

import com.zhulin.commons.CommonBean;
import com.zhulin.dao.UserDao;
import com.zhulin.dto.SignUpParam;
import com.zhulin.entities.User;
import com.zhulin.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

/**
 * @Author:ZHULIN
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public String getRandomId(String uName) {
        // 判断该用户名是否存在
        if (userDao.getUserByName(uName) != null) {
            String randomId = uName + System.currentTimeMillis();
            // 存储信息
            CommonBean.loginMap.put("uname", uName);
            CommonBean.loginMap.put("randomId", randomId);

            // 返回随机数
            return randomId;
        }
        return null;
    }

    @Override
    public User login(User user) {
        // 获取存储器中的随机数
        String randomId = (String) CommonBean.loginMap.get("randomId");
        // 获取存储器中的用户名
        String uname = (String) CommonBean.loginMap.get("uname");
        // 清空存储器中的信息
        CommonBean.loginMap.remove("uname");
        CommonBean.loginMap.remove("randomId");
        if (!uname.equals(user.getUname())) {
            // 用户名不相等
            return null;
        }
        // 获取数据库中的密码
        User u = userDao.getUserByName(user.getUname());
        String upwd = u.getUpassword();
        // 将随机数和密码组合进行md5加密
        String md5_upwd = DigestUtils.md5DigestAsHex((randomId + upwd).getBytes());
        if (md5_upwd.equals(user.getUpassword())) {
            // 密码符合 登录成功
            u.setUpassword(md5_upwd);
            return u;
        }
        return null;
    }

    @Override
    public Boolean signUpUser(SignUpParam signUpParam) {
        int isSign = userDao.signUpUser(signUpParam.getUser());
        if (isSign != 1) {
            // 注册失败
            return false;
        }
        return true;
    }
}
