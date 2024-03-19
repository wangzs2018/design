package com.book.pay.strategy.context;

import com.book.pay.strategy.PayStrategyInterface;
import com.book.pojo.Order;

/**
 * 策略环境类
 */
public class PayContext extends AbstractPayContext{
    private PayStrategyInterface payStrategy;

    public PayContext(PayStrategyInterface payStrategy) {
        this.payStrategy = payStrategy;
    }

    @Override
    public String execute(Order order) {
        return this.payStrategy.pay(order);
    }
}
