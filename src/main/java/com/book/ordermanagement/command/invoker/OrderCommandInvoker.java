package com.book.ordermanagement.command.invoker;

import com.book.ordermanagement.command.OrderCommandInterface;
import com.book.pojo.Order;

public class OrderCommandInvoker {
    public void invoke(OrderCommandInterface command, Order order) {
        command.execute(order);
    }
}
