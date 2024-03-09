package com.book.dprecated.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeprecatedOrderContext {
    //移除currentState并移除set方法，转而引入四个具体状态类
    @Autowired
    private DeprecatedCreateOrder deprecatedCreateOrder;
    @Autowired
    private DeprecatedPayOrder deprecatedPayOrder;
    @Autowired
    private DeprecatedSendOrder deprecatedSendOrder;
    @Autowired
    private DeprecatedReceiveOrder deprecatedReceiveOrder;

    public DeprecatedOrder createOrder(String orderId, String productId) {
        //创建订单，使用deprecatedCreateOrder
        DeprecatedOrder order = deprecatedCreateOrder.createOrder(orderId, productId, this);
        return order;
    }
    public DeprecatedOrder payOrder(String orderId) {
        //支付订单，使用deprecatedPayOrder
        DeprecatedOrder order = deprecatedPayOrder.payOrder(orderId, this);
        return order;
    }
    public DeprecatedOrder sendOrder(String orderId) {
        //订单发货，使用 deprecatedSendOrder
        DeprecatedOrder order = deprecatedSendOrder.sendOrder(orderId, this);
        return order;
    }
    public DeprecatedOrder receiveOrder(String orderId) {
        //订单签收，使用 deprecatedReceiveOrder
        DeprecatedOrder order = deprecatedReceiveOrder.receiveOrder(orderId, this);
        return order;
    }
}
