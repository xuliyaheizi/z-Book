package com.zhulin.constants;

/**
 * @Author:ZHULIN
 * @Date:2022/4/18
 * @Description: URL地址
 */
public class UrlConstant {

    /**
     * 全国城市列表
     */
    public static final String SITE_URL = "https://www.mxnzp.com/api/address/list?app_id=mumfqlfftgqtnoql&app_secret" +
            "=SDY2SG1wRG1JaUcwTE96dU1LRHFGUT09";

    /**
     * CSDN热榜信息
     */
    public static final String CSDN_URL = "https://www.coderutil.com/api/resou/v1/csdn";

    /**
     * 服务提供者接口
     */
    public static final String BASE_URL = "http://zhulinz.top:8001/provide";
    //public static final String BASE_URL = "http://localhost:9001/provide";

    /**
     * 发送验证码信息
     */
    public static final String CAPTCHA_URL =
            "https://gyytz.market.alicloudapi" + ".com/sms/smsSend?smsSignId=2e65b1bb3d054466b82f0c9d125465e2" +
                    "&templateId"
                    + "=908e94ccf08b4476ba6c876d13f084ad&param=**code**:%s,**minute**:5&mobile=%s";
}
