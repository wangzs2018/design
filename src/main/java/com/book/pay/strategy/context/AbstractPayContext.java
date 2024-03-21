package com.book.pay.strategy.context;

import com.book.pojo.Order;
import org.springframework.stereotype.Component;

/**
 * 抽象产品类
 */
@Component
public abstract class AbstractPayContext {
    public abstract String execute(Order order);
}
