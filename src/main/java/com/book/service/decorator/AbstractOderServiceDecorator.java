package com.book.service.decorator;

import com.book.pojo.Order;
import com.book.service.inter.OrderServiceInterface;

//实现 OrderServiceInterface
public abstract class AbstractOderServiceDecorator implements OrderServiceInterface {
    //关联 OrderServiceInterface
    private OrderServiceInterface orderServiceInterface;
    //为 OrderServiceInterface提供初始化方法，我们采用set方法
    public void setOrderServiceInterface(OrderServiceInterface orderServiceInterface) {
        this.orderServiceInterface = orderServiceInterface;
    }
    //覆写 createOrder 方法,但不改变方法逻辑，直接调用orderServiceInterface的 createOrder 方法
    @Override
    public Order createOrder(String productId) {
        return this.orderServiceInterface.createOrder(productId);
    }
    //覆写 send 方法,但不改变方法逻辑，直接调用orderServiceInterface的 send 方法
    @Override
    public Order send(String orderId) {
        return this.orderServiceInterface.send(orderId);
    }
    //覆写 receive 方法,但不改变方法逻辑，直接调用orderServiceInterface的 receive 方法
    @Override
    public Order receive(String orderId) {
        return this.orderServiceInterface.receive(orderId);
    }
    //覆写 getPayUrl 方法,但不改变方法逻辑，直接调用orderServiceInterface的 getPayUrl 方法
    @Override
    public String getPayUrl(String orderId, Float price, String payType) {
        return this.orderServiceInterface.getPayUrl(orderId,price,payType);
    }
    //覆写pay方法,但不改变方法逻辑，直接调用orderServiceInterface的pay方法
    @Override
    public Order pay(String orderId) {
        return this.orderServiceInterface.pay(orderId);
    }

    /**
     * 定义新的方法，根据userId和productId更新用户积分、发放红包
     * @param productId 商品id
     * @param serviceLevel  服务等级
     * @param price 商品价格
     */
    protected abstract void updateScoreAndSendRedPaper(String productId, int serviceLevel, float price);
}
