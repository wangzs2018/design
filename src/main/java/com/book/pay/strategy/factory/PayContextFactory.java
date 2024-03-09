package com.book.pay.strategy.factory;

import com.book.pay.strategy.PayStrategyInterface;
import com.book.pay.strategy.context.PayContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PayContextFactory extends  AbstractPayContextFactory<PayContext> {
    //创建Map数据结构作为缓存 存储PayContext
    private static final Map<String, PayContext> payContexts = new ConcurrentHashMap();
    @Override
    public PayContext getContext(Integer payType) {
        //根据payType定位枚举类
        StrategyEnum strategyEnum =
                payType == 1 ? StrategyEnum.alipay :
                payType == 2 ? StrategyEnum.wechat :
                null;
        if(strategyEnum == null) {
            throw new UnsupportedOperationException("payType not supported!");
        }
        //尝试从map中获取 PayContext
        PayContext context = payContexts.get(strategyEnum.name());
        //第一次调用，context为空
        if(context == null) {
            try {
                //通过反射，创建具体策略类
                PayStrategyInterface payStrategy = (PayStrategyInterface) Class.forName(strategyEnum.getValue()).newInstance();
                //将具体策略类作为入参，创建PayContext类
                PayContext payContext = new PayContext(payStrategy);
                //将PayContext类存储Map缓存，下次可直接使用
                payContexts.put(strategyEnum.name(), payContext);
            } catch (Exception e) {
                throw new UnsupportedOperationException("Get payStrategy failed!");
            }
        }
        return payContexts.get(strategyEnum.name());
    }
}
