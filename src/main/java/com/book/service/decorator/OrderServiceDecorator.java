package com.book.service.decorator;

import com.book.pojo.Order;
import com.book.pojo.Products;
import com.book.repo.ProductsRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceDecorator extends AbstractOderServiceDecorator{
    //引入apollo配置中心的消息超时时间
    @Value("${delay.service.time}")
    private String delayServiceTime;
    //注入productsRepository，查询商品信息
    @Autowired
    private ProductsRepository productsRepository;
    //注入rabbitTemplate，发送消息
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 将pay方法与updateScoreAndSendRedPaper方法进行逻辑结合
     * @param orderId   订单id
     * @param serviceLevel  服务等级
     * @param price 商品价格
     * @return
     */
    public Order decoratorPay(String orderId, int serviceLevel, float price) {
        //调用原有 OrderService 的逻辑
        Order order = super.pay(orderId);
        //新的逻辑，更新积分、发放用户红包
        try {
            this.updateScoreAndSendRedPaper(order.getProductId(), serviceLevel, price);
        } catch (Exception e) {
            //重试机制。此处积分更新不能影响 支付主流程
        }
        return order;
    }

    /**
     * 覆写 OrderServiceDecorator 中的抽象方法，具体实现积分更新和红包发放
     * @param productId 商品id
     * @param serviceLevel  服务等级
     * @param price 商品价格
     */
    @Override
    protected void updateScoreAndSendRedPaper(String productId, int serviceLevel, float price) {
        switch (serviceLevel) {
            case 0:
                //根据价格的百分之一更新积分
                int score = Math.round(price) / 100;
                System.out.println("正常处理，为用户更新积分！score = " + score);
                //根据商品属性发放红包
                Products product = productsRepository.findByProductId(productId);
                if(product != null && product.getSendRedBag() == 1) {
                    System.out.println("正常处理，为用户发放红包！productId = " + productId);
                }
                break;
            case 1:
                MessageProperties properties = new MessageProperties();
                //设置消息过期时间
                properties.setExpiration(delayServiceTime);
                Message msg = new Message(productId.getBytes(), properties);
                //像正常队列中发送消息
                rabbitTemplate.send("normalExchange", "myRKey", msg);
                System.out.println("延迟处理,时间="+delayServiceTime);
                break;
            case 2:
                System.out.println("暂停服务！");
                break;
            default:
                throw new UnsupportedOperationException("不支持的服务级别！");
        }
    }

}
