package com.book.dprecated.state;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeprecatedOrder {
    private String orderId;
    private String productId;
    private String state;
}
