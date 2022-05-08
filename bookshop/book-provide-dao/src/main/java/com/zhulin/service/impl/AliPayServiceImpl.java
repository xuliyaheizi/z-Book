package com.zhulin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.zhulin.dto.DeleteMultiple;
import com.zhulin.dto.OrderBookDto;
import com.zhulin.entities.OrderInfo;
import com.zhulin.enums.OrderStatus;
import com.zhulin.service.AliPayService;
import com.zhulin.service.BookService;
import com.zhulin.service.CartService;
import com.zhulin.service.OrderInfoService;
import com.zhulin.vo.CartBookVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:ZHULIN
 * @Date: 2022/4/30
 * @Description: 支付宝支付服务
 */
@Service
@Slf4j
public class AliPayServiceImpl implements AliPayService {
    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private BookService bookService;
    @Resource
    private CartService cartService;

    @Resource
    private AlipayClient alipayClient;

    @Value("${alipay.notify-url}")
    private String notifyUrl;

    @Value("${alipay.return-url}")
    private String returnUrl;

    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public String tradeCreate(OrderBookDto orderBookDto) {
        try {
            //生成订单
            log.info("生成订单");
            OrderInfo orderInfo = orderInfoService.createOrder(orderBookDto);

            //调用支付宝接口
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            //配置需要的公共请求参数
            //支付完成后，支付宝向谷粒学院发起异步通知的地址
            request.setNotifyUrl(notifyUrl);
            //支付完成后，我们想让页面跳转回谷粒学院的页面，配置returnUrl
            request.setReturnUrl(returnUrl);

            //组装当前业务方法的请求参数
            JSONObject bizContent = new JSONObject();
            bizContent.put("out_trade_no", orderInfo.getOno());
            bizContent.put("total_amount", orderInfo.getOtotalprice());
            bizContent.put("subject", "订单测试");
            bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

            // 商品明细信息，按需传入
            JSONArray goodsDetail = new JSONArray();
            for (CartBookVo cartBookVo : orderBookDto.getBuyBookList()) {
                JSONObject good = new JSONObject();
                good.put("goods_id", cartBookVo.getBook().getBid());
                good.put("goods_name", cartBookVo.getBook().getBname());
                good.put("quantity", cartBookVo.getBookNumber());
                double onePrice = cartBookVo.getItemPriceId() / cartBookVo.getBookNumber();
                BigDecimal decimal = new BigDecimal(onePrice);
                onePrice = decimal.setScale(2, RoundingMode.valueOf(2)).doubleValue();
                good.put("price", onePrice);
                goodsDetail.add(good);
            }
            bizContent.put("goods_detail", goodsDetail);

            request.setBizContent(bizContent.toString());

            //执行请求，调用支付宝接口
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);

            if (response.isSuccess()) {
                log.info("调用成功，返回结果");
                return response.getBody();
            } else {
                log.info("调用失败，返回码 ===> " + response.getCode() + ", 返回描述 ===> " + response.getMsg());
                throw new RuntimeException("创建支付交易失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new RuntimeException("创建支付交易失败");
        }
    }

    /**
     * 处理订单
     * @param params 支付宝回调订单信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void processOrder(Map<String, String> params) {

        log.info("处理订单");
        //获取订单号
        String orderNo = params.get("out_trade_no");

        /*在对业务数据进行状态检查和处理之前，
        要采用数据锁进行并发控制，
        以避免函数重入造成的数据混乱*/
        //尝试获取锁：
        // 成功获取则立即返回true，获取失败则立即返回false。不必一直等待锁的释放
        if (lock.tryLock()) {
            try {

                //处理重复通知
                //接口调用的幂等性：无论接口被调用多少次，以下业务执行一次
                OrderInfo orderInfo = orderInfoService.getOrderByOrderNo(orderNo);
                String orderStatus = orderInfo.getOstatus();
                if (!OrderStatus.NOTPAY.getType().equals(orderStatus)) {
                    return;
                }

                //更新订单状态
                orderInfoService.updateStatusByOrderNo(orderNo, OrderStatus.SUCCESS);
                //获取用户编号
                int uid = orderInfo.getUid();
                //获取用户订单
                OrderBookDto orderBookDto = cartService.getOrderBook(uid);
                //订单内图书编号集合
                List<Integer> bidList = new ArrayList<>();
                //订单内图书信息
                List<CartBookVo> bookVos = orderBookDto.getBuyBookList();
                //循环图书
                bookVos.forEach(book -> {
                    //存储图书编号
                    bidList.add(book.getBook().getBid());
                    //修改图书数量
                    bookService.afterBuy(book.getBook(), book.getBookNumber());
                });
                //清除购物车信息
                DeleteMultiple deleteMultiple = new DeleteMultiple();
                deleteMultiple.setUid(uid);
                deleteMultiple.setBidList(bidList);
                cartService.deleteMultiple(deleteMultiple);

            } finally {
                //要主动释放锁
                lock.unlock();
            }
        }
    }
}
