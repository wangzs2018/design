package com.book.ordermanagement.command.receiver;

import com.book.pojo.Order;
import org.springframework.stereotype.Component;

/**
 * 命令接收者/命令实际的执行者
 * 遵守单一职责原则,可以根据需求创建多个Receiver
 */
@Component
public class OrderCommandReceiver {
    public void action(Order order){
        switch(order.getOrderState()) {
            case ORDER_WAIT_PAY:
                System.out.println("创建订单：order = " + order);
                System.out.println("存入DB!");
                return;
            case ORDER_WAIT_SEND:
                System.out.println("支付订单：order = " + order);
                System.out.println("存入DB!");
                System.out.println("通过queue通知财务部门");
                System.out.println("通过queue通知物流部门");
            case ORDER_WAIT_RECEIVE:
                System.out.println("订单发货：order = " + order);
                System.out.println("存入DB!");
                return;
            case ORDER_FINISH:
                System.out.println("接收订单：order = " + order);
                System.out.println("存入DB!");
                return;
            default:
                throw new UnsupportedOperationException("Order state error");
        }
    }
}
