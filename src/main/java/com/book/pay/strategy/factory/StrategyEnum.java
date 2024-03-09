package com.book.pay.strategy.factory;

public enum StrategyEnum {
    //定义支付宝支付策略类
    alipay("com.book.pay.strategy.AlipayStrategy"),
    //定义微信支付策略类
    wechat("com.book.pay.strategy.WechatStrategy");
    String value = "";
    StrategyEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
}
