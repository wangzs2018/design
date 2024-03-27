package com.book.transaction.mediator;

import com.book.transaction.colleague.AbstractCustomer;

/**
 * 抽象中介者,定义不通同事之间的信息交互方法
 */
public abstract class AbstractMediator {
    /**
     * 定义信息交互方法，供子类进行实现
     * @param orderId   订单id
     * @param targetCustomer    目标用户,比如张三请李四帮忙支付,那么李四便是目标用户
     * @param customer  当前用户,抽象同事类,中介者需要根据该参数确定调用者的角色,购买者或者帮忙支付者
     * @param payResult 支付结果,只有支付成功后次参数才不为空
     */
    public abstract void messageTransfer(String orderId,String targetCustomer, AbstractCustomer customer, String payResult);
}
