package com.book.dprecated.state;

import com.book.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeprecatedCreateOrder extends DeprecatedAbstractOrderState {
    //引入redis，将新生成的订单存放到 redis
    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Override
    protected DeprecatedOrder createOrder(String orderId, String productId) {
        //创建订单对象，设置状态为 ORDER_WAIT_PAY
        DeprecatedOrder order = DeprecatedOrder.builder()
                .orderId(orderId)
                .productId(productId)
                .state(ORDER_WAIT_PAY)
                .build();
        //将新订单存入 redis缓存，15分钟后失效
        redisCommonProcessor.set(orderId, order, 900);
        //观察者模式：发送订单创建Event（4.5小节和4.6小节进行实现）
        super.notifyObserver(orderId, ORDER_WAIT_PAY);
        return order;
    }
}
