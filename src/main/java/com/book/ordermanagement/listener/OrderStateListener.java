package com.book.ordermanagement.listener;

import com.book.ordermanagement.command.OrderCommand;
import com.book.ordermanagement.command.invoker.OrderCommandInvoker;
import com.book.ordermanagement.state.OrderState;
import com.book.ordermanagement.state.OrderStateChangeAction;
import com.book.pojo.Order;
import com.book.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;


@Component
@WithStateMachine(name="orderStateMachine")
public class OrderStateListener {

    @Autowired
    private OrderCommand orderCommand;

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @OnTransition(source = "ORDER_WAIT_PAY", target = "ORDER_WAIT_SEND")
    public boolean payToSend(Message<OrderStateChangeAction> message){
        //从redis中获取 订单，并判断当前订单状态是否为 待支付
        Order order = (Order) message.getHeaders().get("order");
        if(order.getOrderState() != OrderState.ORDER_WAIT_PAY) {
            throw new UnsupportedOperationException("Order state error!");
        }
        //支付逻辑（第五章进行多种类支付功能）
        //支付成功后修改 订单状态为 待发货，并更新redis缓存
        order.setOrderState(OrderState.ORDER_WAIT_SEND);
        redisCommonProcessor.set(order.getOrderId(), order);
        //命令模式进行相关处理（本章4.10节和4.11节进行实现）
        new OrderCommandInvoker().invoke(orderCommand, order);
        return true;
    }
    @OnTransition(source = "ORDER_WAIT_SEND", target = "ORDER_WAIT_RECEIVE")
    public boolean sendToReceive(Message<OrderStateChangeAction> message){
        Order order = (Order) message.getHeaders().get("order");
        if(order.getOrderState() != OrderState.ORDER_WAIT_SEND) {
            throw new UnsupportedOperationException("Order state error!");
        }
        order.setOrderState(OrderState.ORDER_WAIT_RECEIVE);
        redisCommonProcessor.set(order.getOrderId(), order);
        new OrderCommandInvoker().invoke(orderCommand, order );
        //命令模式进行相关处理（本章4.10节和4.11节进行实现）
        return true;
    }
    @OnTransition(source = "ORDER_WAIT_RECEIVE", target = "ORDER_FINISH")
    public boolean receiveToFinish(Message<OrderStateChangeAction> message){
        Order order = (Order) message.getHeaders().get("order");
        if(order.getOrderState() != OrderState.ORDER_WAIT_RECEIVE) {
            throw new UnsupportedOperationException("Order state error!");
        }
        order.setOrderState(OrderState.ORDER_FINISH);
        redisCommonProcessor.remove(order.getOrderId());
        new OrderCommandInvoker().invoke(orderCommand, order);
        //命令模式进行相关处理（本章4.10节和4.11节进行实现）
        return true;
    }
}
