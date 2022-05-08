package com.zhulin.controller;

import com.zhulin.bean.JsonModel;
import com.zhulin.commons.CommonBean;
import com.zhulin.dto.*;
import com.zhulin.service.CartService;
import com.zhulin.vo.CartBookVo;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author:ZHULIN
 * @Date: 2022/4/26
 * @Description: 购物车模块
 */
@RestController
@Api(tags = "购物车模块")
@RequestMapping("/provide")
public class CartController {
    @Resource
    private CartService cartService;

    @PostMapping("add/cart")
    @ApiOperation("添加购物车")
    @ApiImplicitParam(type = "body", dataType = "CartDto", name = "cartDto", value = "购物车", required = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "添加成功"),
            @ApiResponse(code = 444, message = "添加失败")
    })
    public JsonModel addBookToCart(@RequestBody CartDto cartDto) {

        boolean addResult = cartService.addNewCart(cartDto);
        if (addResult) {
            return new JsonModel(200, "添加成功");
        }
        return new JsonModel(444, "添加失败");
    }

    @GetMapping("/show/cart/{uid}")
    @ApiOperation("查看购物车")
    @ApiImplicitParam(name = "uid", value = "用户id", required = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 443, message = "暂无数据")
    })
    public JsonModel<Map<String, Object>> showCart(@PathVariable int uid) {
        Map<String, Object> cartBookMap = cartService.showCart(uid);
        List<CartBookVo> cartBookVoList = (List<CartBookVo>) cartBookMap.get("CartBookList");
        if (cartBookVoList != null && cartBookVoList.size() > 0) {
            return new JsonModel<>(200, "查询成功", cartBookMap);
        }
        return new JsonModel<>(443, "暂无数数据");
    }

    @PostMapping("/update/cart")
    @ApiOperation("修改图书")
    @ApiImplicitParam(type = "body", dataType = "UpdateCartDto", name = "updateCartDto", value = "修改图书数量", required =
            true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "修改成功"),
            @ApiResponse(code = 444, message = "修改失败")
    })
    public JsonModel updateCartBookNum(@RequestBody UpdateCartDto updateCartDto) {

        boolean isUpdate = cartService.updateCartBookNum(updateCartDto);
        if (isUpdate) {
            return new JsonModel(200, "修改成功");
        }
        return new JsonModel(444, "修改失败");
    }

    @PostMapping("/deleteSignal/cart")
    @ApiOperation("删除单个图书")
    @ApiImplicitParam(type = "body", dataType = "DeleteSignalDto", name = "deleteSignalDto", value = "删除单个图书参数",
            required = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "删除成功"),
            @ApiResponse(code = 444, message = "删除失败")
    })
    public JsonModel deleteSignal(@RequestBody DeleteSignalDto deleteSignalDto) {

        boolean isDelete = cartService.deleteSignal(deleteSignalDto);
        if (isDelete) {
            return new JsonModel(200, "删除成功");
        }
        return new JsonModel(444, "删除失败");
    }

    @PostMapping("/deleteMultiple/cart")
    @ApiOperation("删除购物车中多个图书")
    @ApiImplicitParam(type = "body", dataType = "DeleteMultiple", name = "deleteMultiple", required = true)
    public JsonModel deleteMultiple(@RequestBody DeleteMultiple deleteMultiple) {
        boolean isDeleted = cartService.deleteMultiple(deleteMultiple);
        if (isDeleted) {
            return new JsonModel(200, "删除成功");
        }
        return new JsonModel(444, "删除失败");
    }

    @PostMapping("/cart/addOrderBook")
    @ApiOperation("存储需下单的图书信息")
    @ApiImplicitParam(type = "body", dataType = "OrderBookDto", name = "orderBookDto", value = "订单图书信息", required =
            true)
    public JsonModel addOrderBook(@RequestBody OrderBookDto orderBookDto) {
        CommonBean.OrderBookMap.put(orderBookDto.getUid(), orderBookDto);
        if (CommonBean.OrderBookMap != null && CommonBean.OrderBookMap.size() > 0) {
            return new JsonModel(200, "存储成功");
        }
        return new JsonModel(444, "存储失败");
    }

    @GetMapping("/cart/getOrderBook/{uid}")
    @ApiOperation("获取图书订单信息")
    @ApiImplicitParam(name = "uid", value = "用户编号", required = true)
    public JsonModel<OrderBookDto> getOrderBook(@PathVariable int uid) {
        OrderBookDto orderBookDto = cartService.getOrderBook(uid);
        if (orderBookDto != null) {
            return new JsonModel<>(200, "获取成功", orderBookDto);
        }
        return new JsonModel<>(444, "获取失败");
    }
}
