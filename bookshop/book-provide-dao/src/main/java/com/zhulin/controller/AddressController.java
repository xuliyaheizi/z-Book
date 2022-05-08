package com.zhulin.controller;

import com.zhulin.bean.JsonModel;
import com.zhulin.dto.AddressDto;
import com.zhulin.entities.Address;
import com.zhulin.entities.User;
import com.zhulin.service.AddressService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:ZHULIN
 * @Date: 2022/4/19
 * @Description: 收货地址控制层
 */
@RestController
@RequestMapping("/provide")
@Api(tags = "收货地址模块")
public class AddressController {
    @Resource
    private AddressService addressService;

    @PostMapping("/addAddress")
    @ApiOperation("添加收获地址")
    @ApiImplicitParam(type = "body", dataType = "AddressDto", name = "addressDto", value = "添加地址参数信息", required = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "添加成功"),
            @ApiResponse(code = 444, message = "添加失败")
    })
    public JsonModel addAddress(@RequestBody AddressDto addressDto) {
        //判断地址是否添加成功
        Boolean isAddress = addressService.addNewAddress(addressDto);
        if (isAddress) {
            return new JsonModel(200, "添加成功");
        }
        return new JsonModel(444, "添加失败");
    }

    @GetMapping("/showAddress/{uid}")
    @ApiOperation("获取用户的收获地址")
    @ApiImplicitParam(name = "uname", value = "用户名", required = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 444, message = "查询失败")
    })
    public JsonModel<List<Address>> showAddress(@PathVariable int uid) {
        //获取用户id
        User user = new User();
        user.setUid(uid);
        //获取该用户的收获地址
        List<Address> addressList = addressService.getAddressByUid(user);
        if (addressList.size() > 0) {
            return new JsonModel<>(200, "查询成功", addressList);
        }
        return new JsonModel<>(444, "查询失败");
    }

    @PostMapping("/deleteAddress/{aid}")
    @ApiOperation("删除地址")
    @ApiImplicitParam(name = "aid", value = "地址id", required = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "删除成功"),
            @ApiResponse(code = 444, message = "删除失败")
    })
    public JsonModel deleteAddress(@PathVariable Integer aid) {
        Address address = new Address();
        address.setAid(aid);
        //判断地址是否删除成功
        Boolean isDelete = addressService.deleteAddress(address);
        if (isDelete) {
            return new JsonModel(200, "删除成功");
        }
        return new JsonModel(444, "删除失败");
    }

    @GetMapping("/get/default/address/{uid}")
    @ApiOperation("获取默认地址")
    @ApiImplicitParam(name = "uid", value = "用户编号", required = true)
    public JsonModel<Address> getDefaultAddress(@PathVariable int uid) {
        Address address = addressService.getDefaultAddress(uid);
        if (address != null) {
            return new JsonModel<>(200, "获取成功", address);
        }
        return new JsonModel<>(444, "获取失败");
    }
}
