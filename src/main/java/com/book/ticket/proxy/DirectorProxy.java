package com.book.ticket.proxy;

import com.book.ticket.director.AbstractDirector;
import com.book.ticket.director.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DirectorProxy extends AbstractDirector {
    @Autowired
    private Director director;

    @Override
    public Object buildTicket(String type, String productId, String content, String title, String bankInfo, String taxId) {
        //前置处理：通过productId获取商品信息
        String product = this.getProduct(productId);
        //前置处理，校验银行卡信息
        if(bankInfo != null && !this.validateBankInfo(bankInfo)) {
            return null;
        }
        //代理 director类的buildTicket方法
        return director.buildTicket(type, product,content,title,bankInfo,taxId);
    }
    //前置处理：通过productId获取商品信息
    private String getProduct(String productId) {
        return "通过productId获取商品信息";
    }
    //前置处理，校验银行卡信息
    private boolean validateBankInfo(String bankInfo) {
        System.out.println("银行卡校验逻辑！");
        return true;
    }
}
