package com.book.bridge.abst;

import com.book.bridge.function.RegisterLoginFuncInterface;
import com.book.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractRegisterLoginComponent {
    // 面向接口编程,引入RegisterLoginFuncInterface接口属性,此处为"桥"之所在
    protected RegisterLoginFuncInterface funcInterface;

    public AbstractRegisterLoginComponent(RegisterLoginFuncInterface funcInterface) {
        validate(funcInterface);
        this.funcInterface = funcInterface;
    }

    protected final void validate(RegisterLoginFuncInterface funcInterface) {
        if(!(funcInterface instanceof RegisterLoginFuncInterface)) {
            throw new UnsupportedOperationException("Unknown register/login function type!");
        }
    }

    public abstract String login(String username, String password);
    public abstract String register(UserInfo userInfo);
    public abstract boolean checkUserExists(String userName);
    public abstract String login3rd(HttpServletRequest request);
}
