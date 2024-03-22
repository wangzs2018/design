package com.book.pay.strategy;

import com.book.pay.strategy.factory.MyEnum;
import com.book.pojo.Order;

public interface PayStrategyInterface {
    public abstract boolean match(MyEnum myEnum);
    String pay(Order order);
}
