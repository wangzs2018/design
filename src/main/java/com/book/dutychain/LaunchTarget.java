package com.book.dutychain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhengshi.wang
 * @description: 投放目标
 * @date 2024/3/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LaunchTarget {
    /**
     * 投放城市
     */
    private String city;
    /**
     * 投放性别
     */
    private String sex;
    /**
     * 投放产品
     */
    private String product;
}
