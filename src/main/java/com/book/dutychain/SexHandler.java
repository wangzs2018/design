package com.book.dutychain;

import com.alipay.api.internal.util.StringUtils;
import com.book.pojo.BusinessLaunch;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 按用户购物性别的筛选责任类
 */
@Slf4j
public class SexHandler extends AbstractBusinessHandler{

    @Override
    public List<BusinessLaunch> processHandler(List<BusinessLaunch> launchList, LaunchTarget launchTarget) {
        //如果launchList中没有数据，直接返回
        if(launchList.isEmpty()) {
            return launchList;
        }
        //按target进行筛选，只保留符合条件的 投放信息
        launchList = launchList.stream().filter(launch -> {
            String sex = launch.getTargetSex();
            if(StringUtils.isEmpty(sex)) {
                return true;
            }
            return sex.equals(launchTarget.getSex());
        }).collect(Collectors.toList());
        log.info("按性别筛选后的结果为：{}", launchList);
        //如果还有下一个责任类，则继续进行筛选
        if(hasNextHandler()) {
            return nextHandler.processHandler(launchList, launchTarget);
        }
        return launchList;
    }
}
