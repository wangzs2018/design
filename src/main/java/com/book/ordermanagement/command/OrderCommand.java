package com.book.ordermanagement.command;

import com.book.ordermanagement.command.receiver.OrderCommandReceiver;
import com.book.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCommand implements OrderCommandInterface{
    //注入 命令接收者
    @Autowired
    private OrderCommandReceiver receiver;

    @Override
    public void execute(Order order) {
        //调用 命令接收者 的action方法，执行命令
        this.receiver.action(order);
    }
}
