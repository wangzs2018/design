package com.book.dprecated.state;

import com.book.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeprecatedPayOrder extends DeprecatedAbstractOrderState {
    //引入redis，存储订单
    @Autowired
    private RedisCommonProcessor redisCommonProcessor;
    //订单支付完成后的下一个状态：待发货
    @Autowired
    private DeprecatedSendOrder deprecatedSendOrder;
    @Override
    protected DeprecatedOrder payOrder(String orderId, DeprecatedOrderContext context) {
        //从redis中取出当前订单，并判断当前订单状态是否为 待支付 状态
        DeprecatedOrder order = (DeprecatedOrder) redisCommonProcessor.get(orderId);
        if(!order.getState().equals(ORDER_WAIT_PAY)){
            throw new UnsupportedOperationException("Order state should be ORDER_WAIT_PAY" +
                    ".But now it's state is : " + order.getState());
        }
        //支付逻辑（第五章讲解）
        //支付完成后，修改订单状态为 待发货，并更新Redis缓存
        order.setState(ORDER_WAIT_SEND);
        redisCommonProcessor.set(orderId, order);
        //观察者模式：发送订单支付Event（4.5小节和4.6小节进行实现）
        super.notifyObserver(orderId, ORDER_WAIT_SEND);
        return order;
    }
}
