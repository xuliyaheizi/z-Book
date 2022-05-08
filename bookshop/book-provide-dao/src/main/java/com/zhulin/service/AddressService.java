package com.zhulin.service;

import com.zhulin.dto.AddressDto;
import com.zhulin.entities.Address;
import com.zhulin.entities.User;

import java.util.List;

/**
 * @Author:ZHULIN
 * @Date: 2022/4/19
 * @Description: 收获地址
 */
public interface AddressService {
    /**
     * 添加收获地址
     * @param addressDto 地址传输数据
     * @return Boolean
     */
    Boolean addNewAddress(AddressDto addressDto);

    /**
     * 查询用户的收获地址
     * @param user 用户
     * @return List<Address>
     */
    List<Address> getAddressByUid(User user);

    /**
     * 删除收获地址
     * @param address 地址
     * @return boolean
     */
    Boolean deleteAddress(Address address);

    /**
     * 获取用户默认地址
     * @param uid 用户编号
     * @return 地址
     */
    Address getDefaultAddress(int uid);
}
