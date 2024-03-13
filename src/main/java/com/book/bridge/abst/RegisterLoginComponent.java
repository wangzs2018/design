package com.book.bridge.abst;

import com.book.bridge.function.RegisterLoginFuncInterface;
import com.book.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;

public class RegisterLoginComponent extends AbstractRegisterLoginComponent{

    // 通过构造函数,传入"桥梁"RegisterLoginFuncInterface的具体类型
    public RegisterLoginComponent(RegisterLoginFuncInterface funcInterface) {
        super(funcInterface);
    }

    @Override
    public String login(String username, String password) {
        // 直接通过桥梁,调用具体实现类的方法
        return funcInterface.login(username, password);
    }

    @Override
    public String register(UserInfo userInfo) {
        return funcInterface.register(userInfo);
    }

    @Override
    public boolean checkUserExists(String userName) {
        return funcInterface.checkUserExists(userName);
    }

    @Override
    public String login3rd(HttpServletRequest request) {
        return funcInterface.login3rd(request);
    }
}
