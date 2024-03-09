package com.book.pay.strategy.context;

import com.book.pay.strategy.PayStrategyInterface;
import com.book.pojo.Order;

public class PayContext extends AbstractPayContext{
    private PayStrategyInterface payStrategy;

    public PayContext(PayStrategyInterface payStrategy) {
        this.payStrategy = payStrategy;
    }

    public String execute(Order order) {
        return this.payStrategy.pay(order);
    }
}
