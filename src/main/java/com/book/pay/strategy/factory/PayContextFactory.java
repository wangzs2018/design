package com.book.pay.strategy.factory;

import com.book.pay.strategy.PayStrategyInterface;
import com.book.pay.strategy.context.PayContext;
import com.book.utils.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 具体工厂类,支付工厂类
 */
@Component
public class PayContextFactory extends AbstractPayContextFactory<PayContext> {
    @Autowired
    List<PayStrategyInterface> payStrategyInterfaces;

    @Override
    public PayContext getContext(String payType) {
        // 使用filter找到匹配的支付策略，并使用orElseThrow处理不存在的情况
        PayStrategyInterface payStrategy = payStrategyInterfaces.stream()
                .filter(item -> item.match(EnumUtils.getEnumByCode(payType, PayTypeEnum.class)))
                .findAny()
                .orElseThrow(() -> new UnsupportedOperationException("Unsupported payType: " + payType));
        // 返回包含支付策略的PayContext对象
        return new PayContext(payStrategy);
    }
}
