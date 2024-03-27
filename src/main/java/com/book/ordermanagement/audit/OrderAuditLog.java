package com.book.ordermanagement.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 订单审计日志
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAuditLog {
    //当前用户信息
    private String account;
    //用户操作
    private String action;
    //用户具体操作时间
    private Date date;
    //当前订单Id
    private String orderId;
    //其他额外信息
    private Object details;
}
