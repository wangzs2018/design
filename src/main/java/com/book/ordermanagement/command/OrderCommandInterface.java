package com.book.ordermanagement.command;

import com.book.pojo.Order;

public interface OrderCommandInterface {
    void execute(Order order);
}
