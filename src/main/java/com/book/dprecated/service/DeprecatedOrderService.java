package com.book.dprecated.service;

import com.book.dprecated.state.DeprecatedOrder;
import com.book.dprecated.state.DeprecatedOrderContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeprecatedOrderService {

    // 从实战角度,直接使用状态模式的通用UML类图,会导致@Service逻辑层形同虚设
    // DeprecatedOrderService和DeprecatedOrderContext的代码及其相似
    @Autowired
    private DeprecatedOrderContext orderContext;

    public DeprecatedOrder createOrder(String productId) {
        //订单Id的生成逻辑，笔者有话要说... ...
        String orderId = "OID" + productId;
        return orderContext.createOrder(orderId, productId);
    }

    public DeprecatedOrder pay(String orderId) {
        return orderContext.payOrder(orderId);
    }

    public DeprecatedOrder send(String orderId) {
        return orderContext.sendOrder(orderId);
    }

    public DeprecatedOrder receive(String orderId) {
        return orderContext.receiveOrder(orderId);
    }
}
