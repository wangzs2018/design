package com.book.pay.strategy;

import com.book.pojo.Order;

public class WechatStrategy implements PayStrategyInterface{
    @Override
    public String pay(Order order) {
        return "wechat pay success!";
    }
}
