package com.book.service;

import com.book.ordermanagement.audit.CreateOrderLog;
import com.book.ordermanagement.audit.SendOrderLog;
import com.book.ordermanagement.command.OrderCommand;
import com.book.ordermanagement.command.invoker.OrderCommandInvoker;
import com.book.ordermanagement.state.OrderState;
import com.book.ordermanagement.state.OrderStateChangeAction;
import com.book.pay.facade.PayFacade;
import com.book.pojo.Order;
import com.book.service.inter.OrderServiceInterface;
import com.book.transaction.colleague.AbstractCustomer;
import com.book.transaction.colleague.Buyer;
import com.book.transaction.colleague.Payer;
import com.book.transaction.mediator.Mediator;
import com.book.utils.RedisCommonProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class OrderService implements OrderServiceInterface {
    //依赖注入Spring状态机，与状态机进行交互
    @Autowired
    private StateMachine<OrderState, OrderStateChangeAction> orderStateMachine;
    //依赖注入Spring状态机的RedisPersister存取工具，持久化状态机
    @Autowired
    private StateMachinePersister<OrderState, OrderStateChangeAction, String> stateMachineRedisPersister;
    //依赖注入RedisCommonProcessor，存取订单对象
    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Autowired
    private OrderCommand orderCommand;

    @Autowired
    private PayFacade payFacade;

    @Autowired
    private CreateOrderLog createOrderLog;

    //订单创建
    public Order createOrder(String productId) {
        //此处orderId，需要生成全局的唯一ID，在4.4.2.1小节，笔者已经做过详细引申
        String orderId = "OID"+productId;
        //创建订单并存储到Redis
        Order order = Order.builder()
                .orderId(orderId)
                .productId(productId)
                .orderState(OrderState.ORDER_WAIT_PAY)
                .build();
        redisCommonProcessor.set(order.getOrderId(), order, 900);
        new OrderCommandInvoker().invoke(orderCommand, order);
        System.out.println(createOrderLog.createAuditLog("用户Id","create",orderId));
        return order;
    }

    //订单支付
    public Order pay(String orderId) {
        //从Redis中获取 订单
        Order order = (Order) redisCommonProcessor.get(orderId);
        //包装 订单状态变更 Message，并附带订单操作 PAY_ORDER
        Message message = MessageBuilder
                .withPayload(OrderStateChangeAction.PAY_ORDER).setHeader("order", order).build();
        //将Message传递给Spring状态机

        if(changeStateAction(message,order)) {
            return order;
        }
        return null;
    }
    //订单发送
    public Order send(String orderId) {
        //从Redis中获取 订单
        Order order = (Order) redisCommonProcessor.get(orderId);
        //包装 订单状态变更 Message，并附带订单操作 SEND_ORDER
        Message message = MessageBuilder
                .withPayload(OrderStateChangeAction.SEND_ORDER).setHeader("order", order).build();
        //将Message传递给Spring状态机
        System.out.println(createOrderLog.createAuditLog("用户Id","create",orderId));
        if(changeStateAction(message,order)) {
            return order;
        }
        return null;
    }
    //订单签收
    public Order receive(String orderId) {
        //从Redis中获取 订单
        Order order = (Order) redisCommonProcessor.get(orderId);
        //包装 订单状态变更 Message，并附带订单操作 RECEIVE_ORDER
        Message message = MessageBuilder
                .withPayload(OrderStateChangeAction.RECEIVE_ORDER).setHeader("order", order).build();
        //将Message传递给Spring状态机
        if(changeStateAction(message,order)) {
            return order;
        }
        return null;
    }
    //状态机的相关操作
    private boolean changeStateAction(Message<OrderStateChangeAction> message, Order order) {
        try {
            //启动状态机
            orderStateMachine.start();
            //从Redis缓存中读取状态机，缓存的Key为orderId+"STATE"，这是自定义的，读者可以根据自己喜好定义
            stateMachineRedisPersister.restore(orderStateMachine, order.getOrderId()+"STATE");
            //将Message发送给OrderStateListener
            boolean res = orderStateMachine.sendEvent(message);
            //将更改完订单状态的 状态机 存储到 Redis缓存
            stateMachineRedisPersister.persist(orderStateMachine, order.getOrderId()+"STATE");
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            orderStateMachine.stop();
        }
        return false;
    }

    public String getPayUrl(String orderId, Float price, String payType) {
        Order order = (Order) redisCommonProcessor.get(orderId);
        order.setPrice(price);
        return payFacade.pay(order, payType);
    }
    @Autowired
    private Mediator mediator;

    public void friendPay(String sourceCustomer, String orderId, String targetCustomer, String payResult, String role) {
        //创建中介者
        Buyer buyer = new Buyer(orderId, mediator,sourceCustomer);
        Payer payer = new Payer(orderId, mediator,sourceCustomer);
        HashMap<String, AbstractCustomer> map = new HashMap<>();
        map.put("buyer", buyer);
        map.put("payer", payer);
        mediator.customerInstances.put(orderId, map);
        if(role.equals("B")) {
            buyer.messageTransfer(orderId, targetCustomer, payResult);
        } else if(role.equals("P")) {
            payer.messageTransfer(orderId,targetCustomer,payResult);
        }
    }
}
