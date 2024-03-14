package com.book.dprecated.state;

import com.book.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeprecatedSendOrder extends DeprecatedAbstractOrderState {
    //引入redis，存储订单
    @Autowired
    private RedisCommonProcessor redisCommonProcessor;
    @Override
    protected DeprecatedOrder sendOrder(String orderId) {
        //从redis中取出当前订单，并判断当前订单状态是否为 待发货 状态
        DeprecatedOrder order = (DeprecatedOrder) redisCommonProcessor.get(orderId);
        if(!order.getState().equals(ORDER_WAIT_SEND)){
            throw new UnsupportedOperationException("Order state should be ORDER_WAIT_SEND" +
                    ".But now it's state is : " + order.getState());
        }
        //点击发货后，修改订单状态为 待收货，并更新Redis缓存
        order.setState(ORDER_WAIT_RECEIVE);
        redisCommonProcessor.set(orderId, order);
        //观察者模式：发送订单发货Event（4.5小节和4.6小节进行实现）
        super.notifyObserver(orderId, ORDER_WAIT_RECEIVE);
        return order;
    }
}
