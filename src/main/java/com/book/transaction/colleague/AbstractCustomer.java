package com.book.transaction.colleague;

import com.book.transaction.mediator.AbstractMediator;

public abstract class AbstractCustomer {
    //关联 中介者 对象
    public AbstractMediator mediator;
    //订单id
    public String orderId;
    //当前用户信息
    public String customerName;

    AbstractCustomer(String orderId, AbstractMediator mediator,String customerName) {
        this.mediator = mediator;
        this.orderId = orderId;
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return this.customerName;
    }
    //定义信息交互方法，供子类进行实现
    public abstract void messageTransfer(String orderId, String targetCustomer, String payResult);
}
