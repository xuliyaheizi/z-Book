package com.zhulin.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhulin.dao.UserDao;
import com.zhulin.entities.User;
import com.zhulin.mapper.UserMapper;

/**
 * @Author:ZHULIN
 * @Description:
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByName(String uName) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("uname", uName));
    }

    @Override
    public int signUpUser(User user) {
        return userMapper.insert(user);
    }
}
