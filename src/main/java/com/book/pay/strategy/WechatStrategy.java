package com.book.pay.strategy;

import com.book.pay.strategy.factory.MyEnum;
import com.book.pay.strategy.factory.PayTypeEnum;
import com.book.pojo.Order;
import org.springframework.stereotype.Component;

@Component
public class WechatStrategy implements PayStrategyInterface{
    @Override
    public boolean match(MyEnum myEnum) {
        return myEnum == PayTypeEnum.WECHATPAY;
    }

    @Override
    public String pay(Order order) {
        return "wechat pay success!";
    }
}
