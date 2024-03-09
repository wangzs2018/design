package com.book.bridge.function;

import com.book.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;

public interface RegisterLoginFuncInterface {
    public String login(String account, String password);
    public String register(UserInfo userInfo);
    public boolean checkUserExists(String userName);
    public String login3rd(HttpServletRequest request);
}
