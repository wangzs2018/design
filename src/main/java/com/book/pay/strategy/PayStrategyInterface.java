package com.book.pay.strategy;

import com.book.pojo.Order;

public interface PayStrategyInterface {
    String pay(Order order);
}
