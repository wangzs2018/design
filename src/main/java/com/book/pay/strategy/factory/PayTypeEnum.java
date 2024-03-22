package com.book.pay.strategy.factory;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author zhengshi.wang
 * @description: 支付方式枚举
 * @date 2024/3/21
 */
@AllArgsConstructor
@NoArgsConstructor
public enum PayTypeEnum implements MyEnum<String, String> {
    ALIPAY("alipay", "支付宝支付"),
    WECHATPAY("wechatpay", "微信支付"),
    ;
    private String code;
    private String desc;

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String desc() {
        return this.desc;
    }
}
