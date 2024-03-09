package com.book.bridge.function;

import com.book.bridge.abst.factory.RegisterLoginComponentFactory;
import com.book.pojo.UserInfo;
import com.book.repo.UserRepository;
import com.book.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.AccessibleObject;
import java.util.Date;

@Component
public class RegisterLoginByDefault extends AbstractRegisterLoginFunc implements RegisterLoginFuncInterface {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void initFuncMap() {
        RegisterLoginComponentFactory.funcMap.put("Default", this);
    }

    @Override
    public String login(String account, String password) {
        return super.commonLogin(account, password, userRepository);
    }

    @Override
    public String register(UserInfo userInfo) {
        return super.commonRegister(userInfo, userRepository);
    }

    @Override
    public boolean checkUserExists(String userName) {
        return super.commonCheckUserExists(userName, userRepository);
    }
}
