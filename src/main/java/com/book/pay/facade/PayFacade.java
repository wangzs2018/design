package com.book.pay.facade;

import com.book.pay.strategy.AlipayStrategy;
import com.book.pay.strategy.WechatStrategy;
import com.book.pay.strategy.context.PayContext;
import com.book.pay.strategy.factory.PayContextFactory;
import com.book.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayFacade {
    @Autowired
    private PayContextFactory contextFactory;
    public String pay(Order order, Integer payType) {
        PayContext context = contextFactory.getContext(payType);
        return context.execute(order);
    }
}
//        //获取我们的 策略枚举
//        StrategyEnum strategyEnum = getStrategyEnum(payBody.getType());
//        if(strategyEnum == null) {
//            return false;
//        }
//        //获取我们的策略对象
//        PayStrategy payStrategy = StrategyFactory.getPayStrategy(strategyEnum);
//        //生成我们的策略上下文
//        PayContext context = new PayContext(payStrategy); // TO DO
//        // 装饰一下 context，。立马多了一个功能
//        // 我看这行代码啊，就不顺眼。代理模式搞他。
//        AddFuncDecorator addFuncDecorator = (AddFuncDecorator) AddFuncFactory.getAddFunc(context);
//        //进行扣款
//        return addFuncDecorator.execute(payBody);

//    private static StrategyEnum getStrategyEnum(int type) {
//        switch (type) {
//            case 0:
//                return StrategyEnum.ZfbPayStrategy;
//            case 1:
//                return StrategyEnum.WcPayStrategy;
//            case 2:
//                return StrategyEnum.BkPayStrategy;
//            default:
//                return null;
//        }
//    }

