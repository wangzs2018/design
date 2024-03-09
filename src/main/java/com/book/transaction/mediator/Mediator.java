package com.book.transaction.mediator;

import com.book.transaction.colleague.AbstractCustomer;
import com.book.transaction.colleague.Buyer;
import com.book.transaction.colleague.Payer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class Mediator extends AbstractMediator{
    //关联同事类
//    private AbstractCustomer buyer;
//    private AbstractCustomer payer;
    public static Map<String, Map<String, AbstractCustomer>> customerInstances = new ConcurrentHashMap<>();

//    public void setBuyer(Buyer buyer) {
//        this.buyer = buyer;
//    }
//
//    public void setPayer(Payer payer) {
//        this.payer = payer;
//    }
    //覆写抽象中介者类的方法
    @Override
    public void messageTransfer(String orderId, String targetCustomer,AbstractCustomer customer, String payResult) {
        if(customer instanceof Buyer) {
            AbstractCustomer buyer = customerInstances.get(orderId).get("buyer");
            System.out.println("朋友代付："+ buyer.getCustomerName() +"转发 OrderId " + orderId + " 到用户 " + targetCustomer + " 进行支付." );
        } else if(customer instanceof Payer) {
            AbstractCustomer payer = customerInstances.get(orderId).get("payer");
//            customerInstances.get(orderId).get("payer").messageTransfer(orderId, targetCustomer,null);
            System.out.println("代付完成："+ payer.getCustomerName() +"完成 OrderId " + orderId + " 的支付。通知 " + targetCustomer + "，支付结果：" + payResult );
            customerInstances.remove(orderId);
        }
    }
}
