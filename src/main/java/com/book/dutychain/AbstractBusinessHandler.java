package com.book.dutychain;

import com.book.pojo.BusinessLaunch;

import java.util.List;

public abstract class AbstractBusinessHandler {
    //定义 下一个责任类
    public AbstractBusinessHandler nextHandler;
    //是否有下一个责任类
    public boolean hasNextHandler() {
        return this.nextHandler != null;
    }
    //定义抽象责任类方法
    public abstract List<BusinessLaunch> processHandler(List<BusinessLaunch> launchList, String targetCity, String targetSex, String targetProduct);
}
