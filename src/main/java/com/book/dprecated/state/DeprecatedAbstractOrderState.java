package com.book.dprecated.state;

import com.book.dprecated.DeprecatedConstants;
import com.book.dprecated.observer.DeprecatedAbstractObserver;
import java.util.List;
import java.util.Vector;

public abstract class DeprecatedAbstractOrderState {
    protected final String ORDER_WAIT_PAY = "ORDER_WAIT_PAY"; // 待支付
    protected final String ORDER_WAIT_SEND = "ORDER_WAIT_SEND"; // 待发货
    protected final String ORDER_WAIT_RECEIVE = "ORDER_WAIT_RECEIVE"; // 待收货
    protected final String ORDER_FINISH = "ORDER_FINISH"; // 订单完成

    protected final List<DeprecatedAbstractObserver> observersList = DeprecatedConstants.OBSERVER_LIST;


    /**
     * 创建订单
     * @param orderId
     * @param productId
     * @return
     */
    protected DeprecatedOrder createOrder(String orderId, String productId) {
        throw new UnsupportedOperationException();
    }

    /**
     * 订单支付
     * @param orderId
     * @return
     */
    protected DeprecatedOrder payOrder(String orderId) {
        throw new UnsupportedOperationException();
    }

    /**
     * 订单发送
     * @param orderId
     * @return
     */
    protected DeprecatedOrder sendOrder(String orderId) {
        throw new UnsupportedOperationException();
    }

    /**
     * 订单签收
     * @param orderId
     * @return
     */
    protected DeprecatedOrder receiveOrder(String orderId) {
        throw new UnsupportedOperationException();
    }

    public void notifyObserver(String orderId, String orderState) {
        for(DeprecatedAbstractObserver observer : this.observersList) {
            observer.orderStateHandle(orderId, orderState);
        }
    }
}
