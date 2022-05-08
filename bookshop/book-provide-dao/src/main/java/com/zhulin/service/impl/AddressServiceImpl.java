package com.zhulin.service.impl;

import com.zhulin.dao.AddressDao;
import com.zhulin.dto.AddressDto;
import com.zhulin.entities.Address;
import com.zhulin.entities.User;
import com.zhulin.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author:ZHULIN
 * @Date: 2022/4/19
 * @Description:
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressDao addressDao;

    @Override
    public Boolean addNewAddress(AddressDto addressDto) {
        User user = new User();
        user.setUid(addressDto.getUid());
        Address address = addressDto.getAddress();
        //获取用户地址池 判断该条记录是否是新记录 新记录则为默认状态
        List<Address> addressList = getAddressByUid(user);
        if (addressList != null && addressList.size() > 0) {
            //地址状态1为正常
            address.setAstate(1);
        } else {
            //地址状态0为默认
            address.setAstate(0);
        }
        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String createTime = simpleDateFormat.format(new Date());

        address.setUid(addressDto.getUid());
        address.setAcreatetime(createTime);

        int result = addressDao.addNewAddress(address);
        return result == 1;
    }

    @Override
    public List<Address> getAddressByUid(User user) {
        return addressDao.getAddressByUid(user);
    }

    @Override
    public Boolean deleteAddress(Address address) {
        int result = addressDao.deleteAddress(address);
        return result == 1;
    }

    @Override
    public Address getDefaultAddress(int uid) {
        return addressDao.getDefaultAddress(uid);
    }
}
