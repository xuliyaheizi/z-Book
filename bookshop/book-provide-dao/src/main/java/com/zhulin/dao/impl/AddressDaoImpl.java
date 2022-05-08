package com.zhulin.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhulin.dao.AddressDao;
import com.zhulin.entities.Address;
import com.zhulin.entities.User;
import com.zhulin.mapper.AddressMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:ZHULIN
 * @Date: 2022/4/19
 * @Description:
 */
@Repository
public class AddressDaoImpl implements AddressDao {
    @Resource
    private AddressMapper addressMapper;

    @Override
    public List<Address> getAddressByUid(User user) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", user.getUid()).orderByAsc("astate");
        return addressMapper.selectList(queryWrapper);
    }

    @Override
    public int addNewAddress(Address address) {
        return addressMapper.insert(address);
    }

    @Override
    public int deleteAddress(Address address) {
        return addressMapper.deleteById(address);
    }

    @Override
    public Address getDefaultAddress(int uid) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("astate", 0).eq("uid", uid);
        return addressMapper.selectOne(queryWrapper);
    }
}
