package com.book.pay.facade;

import com.book.pay.strategy.context.PayContext;
import com.book.pay.strategy.factory.PayContextFactory;
import com.book.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayFacade {
    @Autowired
    private PayContextFactory contextFactory;
    public String pay(Order order, String payType) {
        PayContext context = contextFactory.getContext(payType);
        return context.execute(order);
    }
}

