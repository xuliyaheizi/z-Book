package com.zhulin.dao;

import com.zhulin.entities.Address;
import com.zhulin.entities.User;

import java.util.List;

/**
 * @Author:ZHULIN
 * @Date:2022/4/19
 * @Description: 收获地址
 */
public interface AddressDao {

    /**
     * 查询用户的收获地址
     * @param user
     * @return
     */
    List<Address> getAddressByUid(User user);

    /**
     * 添加收获地址
     * @param address
     * @return
     */
    int addNewAddress(Address address);

    /**
     * 删除收获地址
     * @param address
     * @return
     */
    int deleteAddress(Address address);

    /**
     * 获取用户默认地址
     * @param uid
     * @return
     */
    Address getDefaultAddress(int uid);
}
