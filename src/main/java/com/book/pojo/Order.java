package com.book.pojo;

import com.book.ordermanagement.state.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    private String orderId;
    private String productId;
    private OrderState orderState;//订单状态
    private Float price;//商品价格
    private String userId;//当前用户唯一Id
}
