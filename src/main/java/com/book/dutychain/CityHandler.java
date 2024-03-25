package com.book.dutychain;

import com.alipay.api.internal.util.StringUtils;
import com.book.pojo.BusinessLaunch;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 按用户购物城市的筛选责任类
 */
@Slf4j
public class CityHandler extends AbstractBusinessHandler{
    @Override
    public List<BusinessLaunch> processHandler(List<BusinessLaunch> launchList, LaunchTarget launchTarget) {
        //如果launchList中没有数据，直接返回
        if(launchList.isEmpty()) {
            return launchList;
        }
        //按target进行筛选，只保留符合条件的 投放信息
        launchList = launchList.stream().filter(launch -> {
            String city = launch.getTargetCity();
            if(StringUtils.isEmpty(city)) {
                return true;
            }
            List<String> cityList = Arrays.asList(city.split(","));
            return cityList.contains(launchTarget.getCity());
        }).collect(Collectors.toList());
        log.info("按城市筛选后的数据为：{}", launchList);
        //如果还有下一个责任类，则继续进行筛选
        if(hasNextHandler()) {
            return nextHandler.processHandler(launchList, launchTarget);
        }
        return launchList;
    }
}
