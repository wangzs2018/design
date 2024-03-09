package com.book.dprecated.state;

import com.book.dprecated.DeprecatedConstants;
import com.book.dprecated.observer.DeprecatedAbstractObserver;
import java.util.List;
import java.util.Vector;

public abstract class DeprecatedAbstractOrderState {
    protected final String ORDER_WAIT_PAY = "ORDER_WAIT_PAY";
    protected final String ORDER_WAIT_SEND = "ORDER_WAIT_SEND";
    protected final String ORDER_WAIT_RECEIVE = "ORDER_WAIT_RECEIVE";
    protected final String ORDER_FINISH = "ORDER_FINISH";

    protected final List<DeprecatedAbstractObserver> observersList = DeprecatedConstants.OBSERVER_LIST;


    //创建订单
    protected DeprecatedOrder createOrder(String orderId, String productId, DeprecatedOrderContext context) {
        throw new UnsupportedOperationException();
    }
    //订单支付
    protected DeprecatedOrder payOrder(String orderId, DeprecatedOrderContext context) {
        throw new UnsupportedOperationException();
    }
    //订单发送
    protected DeprecatedOrder sendOrder(String orderId, DeprecatedOrderContext context) {
        throw new UnsupportedOperationException();
    }
    //订单签收
    protected DeprecatedOrder receiveOrder(String orderId, DeprecatedOrderContext context) {
        throw new UnsupportedOperationException();
    }

    public void notifyObserver(String orderId, String orderState) {
        for(DeprecatedAbstractObserver observer : this.observersList) {
            observer.orderStateHandle(orderId, orderState);
        }
    }

    public void addObserver(DeprecatedAbstractObserver observer){
        this.observersList.add(observer);
    }

    public void removeObserver(DeprecatedAbstractObserver observer) {
        this.observersList.remove(observer);
    }
}
