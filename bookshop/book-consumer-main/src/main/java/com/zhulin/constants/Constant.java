package com.zhulin.constants;

import org.springframework.stereotype.Component;

/**
 * @Program:bookshop
 * @author：ZHULIN
 * @Description: 常量 @Create：2022/4/6
 */
@Component
public class Constant {

    public static final String CSDN_URL = "https://www.coderutil.com/api/resou/v1/csdn";

    public static final String BASE_URL = "http://localhost:9001/provide";

    public static final String CAPTCHA_URL =
        "https://gyytz.market.alicloudapi" + ".com/sms/smsSend?smsSignId=2e65b1bb3d054466b82f0c9d125465e2&templateId"
            + "=908e94ccf08b4476ba6c876d13f084ad&param=**code**:%s,**minute**:5&mobile=%s";

    public static final String APPCODE = "63119ba31dfd4aa586bed741ee3eae56";

    public static final String SMSSIGNID = "2e65b1bb3d054466b82f0c9d125465e2";

    public static final String TEMPLATEID = "908e94ccf08b4476ba6c876d13f084ad";
}
