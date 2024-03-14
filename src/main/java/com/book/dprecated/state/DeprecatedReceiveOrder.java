package com.book.dprecated.state;

import com.book.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeprecatedReceiveOrder extends DeprecatedAbstractOrderState {
    //引入redis，存储订单
    @Autowired
    private RedisCommonProcessor redisCommonProcessor;
    @Override
    protected DeprecatedOrder receiveOrder(String orderId) {
        //从redis中取出当前订单，并判断当前订单状态是否为 待收付 状态
        DeprecatedOrder order = (DeprecatedOrder) redisCommonProcessor.get(orderId);
        if(!order.getState().equals(ORDER_WAIT_RECEIVE)){
            throw new UnsupportedOperationException("Order state should be ORDER_WAIT_RECEIVE" +
                    ".But now it's state is : " + order.getState());
        }
        //用户收货后，修改订单状态为 订单完成状态，并删除Redis缓存
        order.setState(ORDER_FINISH);
        //观察者模式：发送订单收货Event（4.5小节和4.6小节进行实现）
        super.notifyObserver(orderId, ORDER_FINISH);
        redisCommonProcessor.remove(orderId);
        return order;
    }
}
