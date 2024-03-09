package com.book.ordermanagement.audit;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class PayOrderLog extends AbstractAuditLogProcessor{
    @Override
    protected OrderAuditLog buildDetails(OrderAuditLog auditLog) {
        //增加支付类型和实际支付金额
        HashMap<String, String> extraLog = new HashMap<>();
        extraLog.put("payType","支付宝");
        extraLog.put("price", "100");
        auditLog.setDetails(extraLog);
        return auditLog;
    }
}
